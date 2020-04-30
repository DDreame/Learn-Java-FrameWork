package com.stronger.study.loader;

import com.stronger.study.loader.classloader.MyAppClassloader;
import com.stronger.study.loader.log.PrintMyAppLogger;
import lombok.val;
import lombok.var;

import java.lang.reflect.InvocationTargetException;

public class MyFristApploader {


    /**
     * 由于claaloader不能写两个构造器，所以如果要执行无logger的classloader
     * 应该修改MyFristApploader里面的构造器，同时去掉这里的logger
     */
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        PrintMyAppLogger myAppLogger = new PrintMyAppLogger();
        val a = new MyAppClassloader(MyFristApploader.class.getClassLoader(),myAppLogger);
        Class t = a.loadClass("com.stronger.study.test.TestABC");
        var o = t.newInstance();

        var m = t.getMethod("c",String.class);

        val r = m.invoke(o,"123");

        System.out.println(r);

    }
}
