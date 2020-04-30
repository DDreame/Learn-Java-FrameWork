package com.stronger.study.controller;

import com.stronger.study.controller.annotation.Action;
import com.stronger.study.controller.annotation.after;
import com.stronger.study.controller.annotation.before;
import com.stronger.study.controller.router.BasicRouter;
import com.stronger.study.loader.Loader;
import com.stronger.study.loader.context.MyAppContext;
import com.stronger.study.loader.log.PrintMyAppLogger;
import lombok.var;

import javax.inject.Inject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ControllerLoader implements Loader {
    protected ArrayList<BasicRouter> routers = new ArrayList<>();
    @Inject
    PrintMyAppLogger logger;

    @Inject
    MyAppContext context;

    @Override
    public void load(HashMap<String,Class<?>> loadItem) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {

        for (Map.Entry<String, Class<?>> entry : loadItem.entrySet()) {
            BasicRouter router = new BasicRouter();
            Object instance = context.getBean(entry.getValue(),null);
            if(instance == null) continue;
            String path = instance.getClass().getName();
            Method[] methods = instance.getClass().getMethods();
            ArrayList<Method> beforeMethods = new ArrayList<Method>();
            ArrayList<Method> afterMethods = new ArrayList<Method>();
            for (Method method : methods) {
                var before = method.getAnnotation(before.class);
                if (before != null) {
                    beforeMethods.add(method);
                    logger.logInfo("Loader", "Before" + method.getName() + "正在载入");
                }
                var after = method.getAnnotation(after.class);
                if (after != null) {
                    afterMethods.add(method);
                    logger.logInfo("Loader", "After" + method.getName() + "正在载入");
                }
            }
            router.setBeforeMethods(beforeMethods);
            router.setAfterMethods(afterMethods);
            for (Method method : methods) {
                var action = method.getAnnotation(Action.class);
                if(action != null){
                    router.setActionMethod(method);
                    logger.logInfo("Loader", "Action" + method.getName() + "正在载入");
                    path = path+action.value();
                }
            }
            if(router.getActionMethod() != null){
                router.setClazz(instance);
                router.setPath(path);
                routers.add(router);
                context.putBean(path,router);
            }
        }
    }
}
