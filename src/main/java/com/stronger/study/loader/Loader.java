package com.stronger.study.loader;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * 加载具体功能项的load
 * @author x8140
 */
public interface Loader {
    /**
     *
     * @param loadItem
     */
    public void load(HashMap<String,Class<?>> loadItem) throws Exception;
}
