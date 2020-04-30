package com.stronger.study.loader;

import com.stronger.study.loader.classloader.MyAppClassloader;
import com.stronger.study.loader.config.ConfigManager;
import com.stronger.study.loader.log.PrintMyAppLogger;
import lombok.val;
import lombok.var;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyClassloader {


    /**
     * ClassLoader完全体
     * classloader+配置文件+扫包
     * @param args
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        //logger
        PrintMyAppLogger myAppLogger = new PrintMyAppLogger();
        //存储配置文件
        Map<String, String> myConfig = new ConcurrentHashMap();


        val a = new MyAppClassloader(MyFristApploader.class.getClassLoader(),myAppLogger);
        Class t = a.loadClass("com.stronger.study.test.TestABC");
        try {
            myConfig = new ConfigManager().init(a);
        } catch (Exception e) {
            e.printStackTrace();
            myAppLogger.logError("config Load Fail",e.toString());
        }

        for(Map.Entry<String,String> entry: myConfig.entrySet()){
            System.out.println("key = " +entry.getKey()+" and value = "+ entry.getValue());
        }

        var o = t.newInstance();

        var m = t.getMethod("c",String.class);

        val r = m.invoke(o,"123L");

        System.out.println(r);

    }
}
