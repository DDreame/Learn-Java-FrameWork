package com.stronger.study.loader.context;

import com.stronger.study.annotation.AutoBind;
import com.stronger.study.annotation.Config;
import com.stronger.study.annotation.Default;
import com.stronger.study.loader.log.PrintMyAppLogger;
import lombok.SneakyThrows;
import lombok.val;
import lombok.var;
import javax.inject.Inject;
import javax.inject.Named;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 *
 * @author x8140
 */
public class MyAppContext {
    /**
     * context总体而言是对类和实例的存放
     * context只负责保存类和他们的实例。
     * 存主要通过 putBean进行存放
     * 某些实例之前可能不存在，
     * 所以可能是在newBean的时候设置保存
     *
     * 另外就是getBean 获取实例
     * getBean依次查询实例
     * 从context依据类名查找实例
     *      如果单例模式
     *      查找到结果就返回实例
     *      查找不到就new一个实例进行返回
     *
     *
     *    如果是多例模式
     *    需要进行工厂管理
     *    查找的时候多例的类交给工厂，
     *    由工厂进行返回
     */
    private ConcurrentHashMap<String,ConcurrentHashMap<String,Object>> myAppContext;

    private PrintMyAppLogger logger;
    private Map<String, String> myAppConfig;
   // private ClassLoader appLoader;

    public MyAppContext(PrintMyAppLogger logger, Map<String, String> myAppconfig){
        this.logger = logger;
        this.myAppConfig= myAppconfig;
  //      this.appLoader = apploader;
        myAppContext = new ConcurrentHashMap<String,ConcurrentHashMap<String,Object>>();
        putBean("",this.logger);
        putBean("",this.myAppConfig);
    }
    /**
     * putBean是存放实例的过程
     */
    public void putBean(String name,Object obj){
        if(obj == null){
            return;
        }
        if(name == null){
            name = "";
        }

        putBean(obj.getClass(),name,obj);
    }

    public void putBean(Class<?>  clazz,String instanceName,Object obj){
        String clazzName = clazz.getName();
        var objs = myAppContext.get(clazzName);
        if( objs == null){
            objs = new ConcurrentHashMap<String, Object>();
            myAppContext.put(clazzName,objs);
        }
        objs.put(instanceName,obj);
        logger.logInfo("putBean",clazzName+" : "+instanceName+"  Loding");
        checkAutoBind(clazz.getSuperclass(),instanceName,obj);
        for (Class clazs: clazz.getInterfaces()) {
            checkAutoBind(clazs,instanceName,obj);
        }

    }

    public void checkAutoBind(Class<?> clazz,String name,Object obj){
        var autoBind =clazz.getAnnotation(AutoBind.class);
        if(autoBind != null){
            putBean(clazz,name,obj);
        }
    }

    public Object newBean (Class<?> clazz,String name,boolean save) throws InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        if(clazz == null) {
            return null;
        }
        if(name == null){
            name = "";
        }
        val bean =createBeanInstance(clazz);
        if(bean == null){
            return null;
        }
        if(save){
            if(clazz.getAnnotation(Named.class) != null){
                name =clazz.getAnnotation(Named.class).value();
                putBean(clazz,name,bean);
            }else {
                putBean(clazz,"",bean);
            }

        }
        injectBean(bean);

        return bean;
    }

    private  Object createBeanInstance(Class<?> clazz) throws IllegalAccessException, InstantiationException, InvocationTargetException, ClassNotFoundException {
        val constructorNum =clazz.getConstructors().length;
        if(constructorNum<1) {
            return null;
        }
        val constructors = clazz.getConstructors();

        Constructor<?> inject = null;

        for (Constructor constructor:constructors ) {
            if(constructor.getParameters().length == 0){
                continue;
            }
            if(constructor.getAnnotation(Inject.class) == null){
                continue;
            }
            inject = constructor;
            break;
        }

        if(inject == null){
            return clazz.newInstance();
        }

        var paras = inject.getParameters();
        var objs = new Object[paras.length];
        for (int i = 0;i<paras.length;i++) {
            var para = paras[i];
            if(para.getAnnotation(Named.class) != null){
                var name = para.getAnnotation(Named.class).value();
                objs[i] = getBean(para.getType(),name);
            }else {
                objs[i] = getBean(para.getType(),"");
            }

        }
        return inject.newInstance(objs);
    }

    public void injectBean(Object obj) throws IllegalAccessException, InstantiationException, InvocationTargetException, ClassNotFoundException {
        Class<?> clazz =obj.getClass();
        val fields = new ArrayList< Field >();
        while(clazz != null){
            Field[] fields1 = clazz.getDeclaredFields();
            Collections.addAll(fields, clazz.getDeclaredFields());
            clazz = clazz.getSuperclass();
        }
        for(Field field : fields){
            //Inject 注入实例
            var inject = field.getAnnotation(Inject.class);
            if(inject != null){
                val type = field.getType();
                if(field.getAnnotation(Named.class) != null){
                    val name = field.getAnnotation(Named.class).value();
                    field.setAccessible(true);
                    field.set(obj,getBean(field.getType().getName(),name));
                }else {
                    field.setAccessible(true);
                    field.set(obj,getBean(field.getType().getName(),""));
                }
                continue;
            }
            //Config 注入具体值
            var ans = field.getAnnotations();
            for (Annotation an : ans) {
                System.out.println("--------------");
                val is = an.getClass().getInterfaces();
                for (Class<?> i : is) {
                    System.out.println(i.getName());
                    System.out.println(i.getClassLoader());
                }
                System.out.println("--------------");
            }
            var config = field.getAnnotation(Config.class);
            System.out.println("--------------");
            System.out.println(Config.class.getClassLoader());
            System.out.println("--------------");
            if(config != null){
                val key = config.value();
                val type = field.getType();
                var value = myAppConfig.get(key);

                if(value == null){
                    val defaull = field.getDeclaredAnnotation(Default.class);
                        if (defaull != null) {
                        field.setAccessible(true);
                        field.set(obj,defaull.value());
                    }
                }else {
                    field.setAccessible(true);
                    field.set(obj,value);
                }
                continue;
            }

        }

    }

    @SneakyThrows
    public Object get(Class<?> clazz){
        return getBean(clazz,null);
    }

    public Object getBean(Class<?> clazz,String name) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if(name == null){
            name = "";
        }
        return getBean(clazz.getName(),name);
    }

    public Object getBean(String clazz,String name) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Object o = myAppContext.get(clazz);
        Object o1;
        if(o == null){
            o1 = newBean(Class.forName(clazz),name,true);
        }else {
            o1 = myAppContext.get(clazz).get(name);
        }
        return o1;
    }
}
