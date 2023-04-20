package com.my_framework.www.webmvc;




import com.my_framework.www.annotation.Controller;
import com.my_framework.www.annotation.RequestMapping;
import com.my_framework.www.context.Impl.ApplicationContextImpl;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 14629
 */
public class DispatcherServlet extends HttpServlet {

    private static final Map<HandlerMapping, HandlerAdapter> handlerAdapters = new HashMap<>();
    private static final List<HandlerMapping> handlerMappings = new ArrayList<>();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            this.doDispatch(req, resp);
        } catch (Exception e) {
            resp.getWriter().write("500 Exception,Details:\r\n"
                    + Arrays.toString(e.getStackTrace()).replaceAll("\\[|\\]", "")
                    .replaceAll(",\\s", "\r\n"));
            e.printStackTrace();
        }
    }



    /**
     * 初始化策略
     */
    public static void initStrategies(ApplicationContextImpl context) {
        //handlerMapping，必须实现
        initHandlerMappings(context);
        //初始化参数适配器，必须实现
        initHandlerAdapters();
    }


    private static void initHandlerMappings(ApplicationContextImpl context) {
        //获取ioc容器的类名
        String[] beanNames = context.getBeanDefinitionNames();
        try {
            for (String beanName : beanNames) {
                Object controller = context.getBean(beanName);
                Class<?> clazz = controller.getClass();
                if (!clazz.isAnnotationPresent(Controller.class)) {
                    //不是controller跳过
                    continue;
                }
                String baseUrl = "";
                //获取Controller的url配置
                if (clazz.isAnnotationPresent(RequestMapping.class)) {
                    RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
                    baseUrl = requestMapping.value();
                }
                //获取Method的url配置
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    //没有加RequestMapping注解的直接忽略
                    if (!method.isAnnotationPresent(RequestMapping.class)) {
                        continue;
                    }
                    //映射URL
                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                    // 像 /api/v1/users 或者/api/v1/users/123 这样的URL 都会被匹配到路径 /api/v1/users/*
                    String regex = ("/" + baseUrl + "/" + requestMapping.value().replaceAll("\\*", ".*")).replaceAll("/+", "/");
                    Pattern pattern = Pattern.compile(regex);
                    handlerMappings.add(new HandlerMapping(controller, method, pattern));
                    System.out.println("Mapped " + regex + "," + method);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void initHandlerAdapters() {
        //一个HandlerMapping对应一个HandlerAdapter
        for (HandlerMapping handlerMapping : handlerMappings) {
            handlerAdapters.put(handlerMapping,HandlerAdapter.getAdapter());
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //1、通过从request中拿到URL，去匹配一个HandlerMapping
        HandlerMapping handler = getHandler(req);
        if (handler == null) {
            //没有找到handler返回404
            resp.sendRedirect("http://localhost:8080/project_war/html/error/404.html");
            return;
        }
        //2、准备调用前的参数
        HandlerAdapter ha = getHandlerAdapter(handler);
        if(ha==null){
            resp.sendRedirect("http://localhost:8080/project_war/html/error/404.html");
            return;
        }
        //3、真正的调用controller的方法
        ha.handle(req, resp, handler);
    }

    private HandlerMapping getHandler(HttpServletRequest req) {
        if (handlerMappings.isEmpty()) {
            return null;
        }

        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath, "").replaceAll("/+", "/");
        for (HandlerMapping handler : handlerMappings) {
            Matcher matcher = handler.getPattern().matcher(url);
            //如果没有匹配上继续下一个匹配
            if (!matcher.matches()) {
                continue;
            }
            return handler;
        }
        return null;
    }
    private HandlerAdapter getHandlerAdapter(HandlerMapping handler) {
        if (handlerAdapters.isEmpty()) {
            return null;
        }
        return handlerAdapters.get(handler);
    }

}    
