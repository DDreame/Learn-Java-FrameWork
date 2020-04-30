package com.stronger.study.controller.action;


import java.util.HashMap;

/**
 * 上下文容器
 * 负责保存路径、保存参数、保存返回值
 * @author x8140
 */

public class DefaultActionContext implements ActionContext{
    /**
     * 路径
     */
    String path;
    /**
     * 参数
     * 保存方式：
     * Map<方法名，Map<参数，具体参数>>
     */
    HashMap<String,HashMap<Integer,Object>> args;
    /**
     * 方法的返回值
     */
    Object result;


    @Override
    public String getPath(){
        return this.path;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public Object getArgs(String methodName,Integer valuesInt){
        return this.args.get(methodName).get(valuesInt);
    }

    @Override
    public void setArgs(HashMap<String,HashMap<Integer,Object>> args){
        this.args = args;
    }

    @Override
    public void setResult(Object object){
        this.result=object;
    }

    @Override
    public Object getResult(){
        return this.result;
    }


}
