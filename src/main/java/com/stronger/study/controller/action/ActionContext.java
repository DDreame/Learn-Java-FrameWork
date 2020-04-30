package com.stronger.study.controller.action;

import java.util.HashMap;

public interface ActionContext {


    /**
     * 返回路径
     * @return
     */
    public String getPath();

    /**
     *
     * 设置路径
     * @param path 路径
     */
    public void setPath(String path);


    /**
     * 根据方法名和参数名
     * 返回值具体的参数内容
     * @param methodName 方法名
     * @param valuesInt 第几个参数
     * @return
     */
    public Object getArgs(String methodName,Integer valuesInt);

    /**
     * 保存上下文参数
     * 一层保存方法名
     * 二层保存参数名与具体值
     * @param args 保存所有参数
     */
    public void setArgs(HashMap<String,HashMap<Integer,Object>> args);
    /**
     * 设置返回值
     * @param object 返回值
     */
    public void setResult(Object object);

    /**
     * 返回值
     * @return
     */
    public Object getResult();
}
