package com.stronger.study.loader


class MyFirstApploader {
    companion object{
        fun start() {

            val a = MyApploder(MyFirstApploader::class.java.classLoader)
            val t = a.loadClass("cf.strongerDream.YuqStudy.TestA")
            val o = t.newInstance()
            var m = t.getMethod("c",String::class.java)
            val r = m.invoke(o,"qwe")
            println(r)

        }
    }

}
