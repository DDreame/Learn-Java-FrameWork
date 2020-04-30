package com.stronger.study.loader.config;

import lombok.val;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author x8140
 */
public class ConfigManager {


    public Map<String,String> init(ClassLoader appClassLoader) throws IOException {
        val in =appClassLoader.getResourceAsStream("testapp.properties");

        val prop=new Properties();
        prop.load(in);

        val map=new ConcurrentHashMap<String,String>();
        for (Object o : prop.keySet()) {
            map.put(o.toString(),prop.get(o).toString());
        }

        return map;
    }
}
