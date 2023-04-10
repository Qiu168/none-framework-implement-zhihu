package com.huangTaiQi.www;

import com.my_framework.www.context.ApplicationContext;
import com.my_framework.www.context.Impl.ApplicationContextImpl;
import com.huangTaiQi.www.service.HelloService;

public class Main {
    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext=new ApplicationContextImpl("application.properties");
        HelloService helloService = (HelloService) applicationContext.getBean("helloServiceImpl");
        helloService.sayHello();
    }
}