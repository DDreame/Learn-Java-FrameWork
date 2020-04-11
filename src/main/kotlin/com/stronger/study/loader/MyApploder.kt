package com.stronger.study.loader

import com.stronger.study.loader.MyApploader
import lombok.SneakyThrows


/**
 *
 * ClassLoader构造
 * 后续拓展有：
 * 实现ClassLoader对类内容自定义
 * 实现Log方法实现自定log显示
 * 实现Hook拦截来修改参数和加载内容
 * 配合配置文件和框架核心实现注解管理和注入等
 * 主要内容是在ClassLoader进行操作其他的类内容
 * @author x81406
 * 2020/4/10
 */
class MyApploder : ClassLoader {

    constructor(parent: ClassLoader): super(parent){

    }




    @SneakyThrows
    fun loadAppClass(name: String): Class<*> {
        val resourceAsSteeam =this.parent.getResourceAsStream((name.replace(".","/") ?: name) +".class")
        var bytes = ByteArray(resourceAsSteeam.available()){0}
        resourceAsSteeam.read(bytes)
        return defineClass(name,bytes,0,bytes.size)
    }

    @SneakyThrows
    override fun loadClass(name: String, resolve: Boolean): Class<*> {
        return loadClass(name, resolve,true);
    }

    private fun loadClass(name: String, resolve: Boolean, enhance: Boolean): Class<*> {
        var c = findLoadedClass(name)
        if (c != null) {
            return c
        }

        if (MyApploader.isBlackListClass(name)) c = parent.loadClass(name)

        if (c == null) if (enhance) c = loadAppClass(name)

        return if (null == c) {
            super.loadClass(name, resolve)
        } else {
            if (resolve) resolveClass(c)
            c
        }
    }


    fun isBlackListClass(name: String): Boolean{
        return (name.startsWith("java.")
                || name.startsWith("javax.")
                || name.startsWith("com.google.")
                || name.startsWith("org.apache.")
                || name.startsWith("sun.")
                || name.startsWith("com.sun.")
                || name.startsWith("com.IceCreamQAQ.Yu.hook")
                || name.startsWith("com.IceCreamQAQ.Yu.loader.enchant"))
    }




}