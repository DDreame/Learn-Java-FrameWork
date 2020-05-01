package com.stronger.study.controller;

import com.stronger.study.controller.action.ActionContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author x8140
 */
public interface MethodInvoker {


    /**
     *
     * 传入方法调用具体方法
     * @param method 传入方法
     * @param instance 传入实例
     * @param actionContext 传入上下文
     * @return 返回方法返回值
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    Object invoke(Method method,Object instance,ActionContext actionContext) throws InvocationTargetException, IllegalAccessException;
}
