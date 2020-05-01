package com.stronger.study.controller.router;

import lombok.Data;
import java.lang.reflect.Method;
import java.util.List;

/**
 *
 * 最小的路由单元
 * @author x8140
 */
@Data
public class BasicRouter {
    /**
     * 基础路径
     */
    String path;
    /**
     * 所有的before方法
     */
    List<Method> beforeMethods;
    /**
     * 主要执行方法
     */
    Method actionMethod;
    /**
     * 所有的After方法
     */
    List<Method> afterMethods;
    /**
     * 方法所在类//或者具体类实例
     */
    Object clazz;
}
