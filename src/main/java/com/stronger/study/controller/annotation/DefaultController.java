package com.stronger.study.controller.annotation;


import com.stronger.study.annotation.LoaderBy;
import com.stronger.study.controller.ControllerLoader;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@LoaderBy(ControllerLoader.class)
public @interface DefaultController {

}
