package com.stronger.study.annotation;
import java.lang.annotation.*;


/**
 * 获取配置文件参数值
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Config {
    String value();
}
