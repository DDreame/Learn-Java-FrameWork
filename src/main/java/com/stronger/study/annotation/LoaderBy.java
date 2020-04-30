package com.stronger.study.annotation;

import com.stronger.study.loader.Loader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoaderBy {

    Class<? extends Loader> value();

}

