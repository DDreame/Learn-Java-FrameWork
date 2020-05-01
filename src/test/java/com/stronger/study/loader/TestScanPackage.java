package com.stronger.study.loader;

import com.stronger.study.controller.ControllerLoader;
import com.stronger.study.controller.action.DefaultActionContext;
import com.stronger.study.controller.router.BasicRouter;
import com.stronger.study.controller.router.RouterAction;
import com.stronger.study.loader.config.ConfigManager;
import com.stronger.study.loader.context.MyAppContext;
import com.stronger.study.loader.log.PrintMyAppLogger;
import lombok.val;
import lombok.var;
import java.util.HashMap;


public class TestScanPackage {

    public static void main(String[] args) throws Exception {
        PrintMyAppLogger logger = new PrintMyAppLogger();

        var myConfig = new ConfigManager().init(TestScanPackage.class.getClassLoader());
        MyAppContext context = new MyAppContext(logger,myConfig);
        context.putBean(null,context);
        context.newBean(TestScanPackage.class,null,true);
        MyApploader myApploader = (MyApploader) context.newBean(MyApploader.class,"apploader",true);

        myApploader.loadApp();
        RouterAction routerAction = (RouterAction) context.newBean(RouterAction.class,null,true);
        val action = new DefaultActionContext();
        action.setPath("com.stronger.study.controller.Testqwe");
        HashMap<Integer,Object> values = new HashMap<>();
        values.put(0,"Value Test!");
        HashMap<String,HashMap<Integer,Object>> arg = new HashMap<>();
        arg.put("test2",values);
        action.setArgs(arg);
        Object o = routerAction.invoke("com.stronger.study.controller.Testqwe",action);

        action.setPath("com.stronger.study.controller.Testaaa");
        arg.clear();
        arg.put("test4",values);
        Object o1 = routerAction.invoke("com.stronger.study.controller.Testaaa",action);
    }
}

