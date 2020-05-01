package com.stronger.study.controller.router;

import com.stronger.study.controller.action.ActionContext;
import com.stronger.study.loader.context.MyAppContext;

import javax.inject.Inject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

/**
 * @author x8140
 */
public class RouterAction {

    @Inject
    MyAppContext context;

    /**
     * ActionContext完成了上下文保存
     * 但是目前的问题是上下文保存没有很好的保存before、after方法的参数
     * 另一种思考方式是，before可以对传入参数进行修改
     * after可以对传出参数进行修改
     */

    public Object invoke(String path, ActionContext actionContext) throws Exception {
        BasicRouter router = (BasicRouter) context.getBean(BasicRouter.class,path);

        List<Method>  methods = router.getBeforeMethods();
        if(methods.size() > 0){
            for(Method method: methods){
                this.invoker(method,router.clazz,actionContext);
            }
        }
        this.invoker(router.actionMethod,router.clazz,actionContext);

        methods = router.getAfterMethods();
        if(methods.size() > 0){
            for(Method method: methods){
                this.invoker(method,router.clazz,actionContext);
            }
        }
        return null;
    }



    public Object invoker(Method method,Object instance,ActionContext actionContext) throws InvocationTargetException, IllegalAccessException {
        //判断返回值
        boolean returnFlag = false;
        //如果返回值类型为void则没有返回值，反之则存在返回值
        String voidS = "void";
        if(!(voidS).equals(method.getReturnType().getName())){
            returnFlag = true;
        }
        //获取所有参数类型
        Parameter[] parameters = method.getParameters();
        //准备一个赋值数组
        Object[] objects = new Object[parameters.length];
        //获取参数的值并进行invoke
        if(parameters.length!=0){
            for(int i = 0;i < parameters.length; i ++){
                objects[i]=actionContext.getArgs(method.getName(),i);
            }
        }
        Object o = null;
        if(returnFlag){
            o = method.invoke(instance,objects);
        }else {
            method.invoke(instance,objects);
        }
        return o;
    }

}
