package com.stronger.study.reflect;
import com.stronger.study.test.TestABC;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method; //导入方法依赖的package包/类
import java.lang.reflect.Parameter;

public class GetArgs {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Method[] methods = TestABC.class.getMethods();
        Parameter[] parameters = methods[0].getParameters();

        Method method = methods[0];
        Object o = GetArgs.class.getClassLoader().loadClass("com.stronger.study.test.TestABC").newInstance();
        Object arg =  "asd";
        method.invoke(o,arg);
    }





    public String TestReflect(String qwe){
        System.out.println("TestQWE");
        return qwe;
    }
}