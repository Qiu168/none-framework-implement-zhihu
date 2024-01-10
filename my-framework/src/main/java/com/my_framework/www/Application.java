package com.my_framework.www;

import com.my_framework.www.core.annotation.SpringBootApplication;
import com.my_framework.www.core.context.ApplicationContext;
import com.my_framework.www.core.context.Impl.GenericApplicationContext;
import com.my_framework.www.utils.ContextUtil;
import com.my_framework.www.utils.StringUtil;
import com.my_framework.www.webmvc.DispatcherServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;


/**
 * @author _qiu
 */
public class Application {
    @SuppressWarnings("UnusedReturnValue")
    public static ApplicationContext run(Class<?> cls){
        SpringBootApplication application = cls.getAnnotation(SpringBootApplication.class);
        if(application==null){
            return null;
        }
        String scanPackage = application.scanPackage();
        if(StringUtil.isEmpty(scanPackage)){
            scanPackage=cls.getPackage().getName();
        }
        System.out.println(scanPackage);
        //1、初始化ApplicationContext，从web.xml中获取参数
        ApplicationContext applicationContext = new GenericApplicationContext("application.properties",scanPackage);
        ContextUtil.setApplicationContext(applicationContext);
        startTomcat(applicationContext);
        return applicationContext;
    }

    private static void startTomcat(ApplicationContext applicationContext) {
        //第一部分
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();
        //第二部分
        Context context = tomcat.addContext("", null);
        System.setProperty("tomcat.util.scan.StandardJarScanFilter.jarsToSkip", "\\,*");
        //第三部分
        Wrapper servlet = Tomcat.addServlet(context,  "testServlet", new DispatcherServlet(applicationContext));//注册Servlet
        servlet.setLoadOnStartup(1);//容器启动初始化Sevlet
        servlet.addMapping("/api/*");//配置Servlet的访问路径
        try {
            tomcat.start();
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }
        tomcat.getServer().await();
    }
}