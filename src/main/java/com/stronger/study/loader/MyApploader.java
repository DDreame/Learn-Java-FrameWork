package com.stronger.study.loader;

import com.stronger.study.annotation.Config;
import com.stronger.study.annotation.LoaderBy;
import com.stronger.study.controller.ControllerLoader;
import com.stronger.study.loader.context.MyAppContext;
import com.stronger.study.loader.log.PrintMyAppLogger;
import lombok.var;

import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class MyApploader {



    @Config("test.scanPackages")
    String scanPackage ;

    @Inject
    ConcurrentHashMap<String,String> config;

    @Inject
    PrintMyAppLogger logger;

    @Inject
    MyAppContext context;

    void loadApp() throws Exception {
        var packetScan = new PacketScan(){};
        var classes = new HashMap<String,Class<?>>();
        classes = packetScan.scanPacket(scanPackage,classes);
        for(Map.Entry entry:classes.entrySet()){
            System.out.println(entry.getKey()+":"+entry.getValue());
        }

        var loadItemsMap = new HashMap<Class<? extends Loader>,HashMap<String,Class<?>>>();

        for(Map.Entry entry: classes.entrySet()){
            loadItemsMap = searchLoader((Class<?>)entry.getValue(),(Class<?>)entry.getValue(),loadItemsMap);
        }
        System.out.println("DebugOne");
        for(Map.Entry entry: loadItemsMap.entrySet()){
           Loader loader = (Loader) context.newBean((Class<?>) entry.getKey(),null,false);
           HashMap<String,Class<?>> classHashMap = (HashMap<String, Class<?>>) entry.getValue();
           loader.load(classHashMap);
           System.out.println("DebugTwo");
        }




    }

    HashMap<Class<? extends Loader>,HashMap<String,Class<?>>> searchLoader(Class<?> loadClass,Class<?> searchClass,HashMap<Class<? extends Loader>,HashMap<String,Class<?>>> loadItemsMap){
        var loaderBy = loadClass.getAnnotation(LoaderBy.class);

        if(loaderBy != null){
            var loader = loaderBy.value();
            if(loadItemsMap.get(loader) == null){
                var l = new HashMap<String,Class<?>>();
                loadItemsMap.put(loader,l);
            }
            var loadItems = loadItemsMap.get(loader);
            loadItems.put(loadClass.getName(),loadClass);
        }

        var annotationInstances = searchClass.getAnnotations();
        for(Annotation annotation:annotationInstances){
            var annotationClass = annotation.getClass().getInterfaces();
            var loadBy = annotationClass[0].getAnnotation(LoaderBy.class);
            if(loadBy == null){
                continue;
            }
            var loader = loadBy.value();
            if(loadItemsMap.get(loader) == null){
                var l = new HashMap<String,Class<?>>();
                loadItemsMap.put(loader,l);
            }
            var loadItems = loadItemsMap.get(loader);
            loadItems.put(loadClass.getName(),loadClass);
        }

        Class<?> superClass = searchClass.getSuperclass();
        if(superClass !=null){
            searchLoader(loadClass,superClass,loadItemsMap);
        }

        var interfacs = searchClass.getInterfaces();
        for(Class<?> clazz : interfacs){
            searchLoader(loadClass,clazz,loadItemsMap);
        }
        return loadItemsMap;
    }



}
