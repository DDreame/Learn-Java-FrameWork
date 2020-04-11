package com.stronger.study.loader

import java.io.File
import java.util.*
import kotlin.collections.HashMap

/**
 *
 * 配置文件加载
 * 后续存在的扩展有：
 * 不同后缀适配 与不同包管理器或打包方式的适配
 * 配置内容管理
 * 存储核心方式改变---读取方式改变
 * 支持配置注解 能够实现自定义注解使用配置文件
 * 当前仅为简单练习，真实实现仍需继续拓展
 * @author  x81406
 * 2020/4/11
 */

class ConfigLoader {

    private var config:HashMap<String,String> =HashMap<String,String>();


    fun  setConfig(classloader : ClassLoader): HashMap<String,String>{
        val classpath = File(classloader.getResource("").toURI())
        val f = File(classpath, "conf")



        return
    }



}
