package com.stronger.study.loader;

import lombok.val;
import lombok.var;

import java.lang.reflect.InvocationTargetException;

public class MyFristApploader {


    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {


        val a = new MyApploader(MyFristApploader.class.getClassLoader());
        Class t = a.loadClass("com.stronger.study.TestA");
        var o = t.newInstance();

        var m = t.getMethod("c",String.class);

        val r = m.invoke(o,"123");

        System.out.println(r);

    }
}
