package com.stronger.study.test;

import com.stronger.study.annotation.Config;



public class TestABC {

    @Config(value = "test.scanPackages")
    String qwe;

    public Object c(String name) {
        System.out.println(qwe);
        System.out.println("fun c run ! name = " + name);
        return "Hello " + name + "!";
    }
}
