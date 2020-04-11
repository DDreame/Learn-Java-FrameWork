package com.stronger.study;

import javax.inject.Named;

public class TestA {

    public Object c(@Named("name") String name) {
        System.out.println("fun c run ! name = " + name);
        return "Hello " + name + "!";
    }
}
