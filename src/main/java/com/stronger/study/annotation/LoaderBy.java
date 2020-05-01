package com.stronger.study.annotation;

import com.stronger.study.loader.Loader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 寻找需要加载的功能项所使用的注解
 * @author x8140
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoaderBy {

    Class<? extends Loader> value();

}

