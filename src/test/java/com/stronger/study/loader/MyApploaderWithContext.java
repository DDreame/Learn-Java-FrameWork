package com.stronger.study.loader;


import com.stronger.study.loader.classloader.MyAppClassloader;
import com.stronger.study.loader.config.ConfigManager;
import com.stronger.study.loader.context.MyAppContext;
import com.stronger.study.loader.log.PrintMyAppLogger;
import lombok.val;
import lombok.var;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



public class MyApploaderWithContext {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        PrintMyAppLogger myAppLogger = new PrintMyAppLogger();
        Map<String, String> myConfig = new ConcurrentHashMap();

      //  val a1 = new MyAppClassloader(MyAppClassloader.class.getClassLoader(),myAppLogger);
        val a = MyApploaderWithContext.class.getClassLoader();
        Class t  = a.loadClass("com.stronger.study.test.TestABC");
        try {
            myConfig = new ConfigManager().init(a);
        } catch (Exception e) {
            e.printStackTrace();
            myAppLogger.logError("config Load Fail",e.toString());
        }

        for(Map.Entry<String,String> entry: myConfig.entrySet()){
            System.out.println("key = " +entry.getKey()+" and value = "+ entry.getValue());
        }
        MyAppContext context = new MyAppContext(myAppLogger,myConfig);
        context.putBean(ClassLoader.class,"appClassLoader",a);
        context.putBean(a.getClass(),"appClassLoader",a);
        context.putBean(ConfigManager.class,"config",myConfig);
        context.newBean(t,"Test",true);
        var o = context.get(t);

        var m = t.getMethod("c",String.class);

        val r = m.invoke(o,"123");

        System.out.println(r);

    }
}
