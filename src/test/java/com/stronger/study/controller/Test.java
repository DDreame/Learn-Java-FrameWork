package com.stronger.study.controller;

import com.stronger.study.controller.annotation.Action;
import com.stronger.study.controller.annotation.DefaultController;
import com.stronger.study.controller.annotation.after;
import com.stronger.study.controller.annotation.before;


@DefaultController
public class Test {

    @before
    public void test1(){
        System.out.println("before Test！");
    }

    @Action("qwe")
    public void test2(String value){
        System.out.println("---------"+value+"--------");
        System.out.println("Action Test!");
    }

    @after
    public void test3() {
        System.out.println("After Test!");
    }
}
