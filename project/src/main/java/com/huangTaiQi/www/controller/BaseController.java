package com.huangTaiQi.www.controller;

import com.my_framework.www.context.Impl.ApplicationContextImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.huangTaiQi.www.utils.StringUtils.toClassName;


/**
 * @author 14629
 * 获取域名最后的方法名，通过反射调用方法
 */
//public class BaseController extends HttpServlet {
//    Logger logger=Logger.getLogger(BaseController.class.getName());
//    private static ApplicationContextImpl applicationContext;
//    static {
//        applicationContext = new ApplicationContextImpl("application.properties");
//        System.out.println("已初始化");
//    }
//    @Override
//    protected void service(HttpServletRequest req, HttpServletResponse resp) {
//        String uri = req.getRequestURI();
//        String methodName = uri.substring(uri.lastIndexOf('/') + 1);
//        Class<? extends BaseController> cls = this.getClass();
//        String className = toClassName(this.toString());
//        Object bean = applicationContext.getBean(className);
//        try {
//            Method method=cls.getMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
//            method.invoke(cls.cast(bean),req,resp);
//        } catch (Exception e) {
//            try {
//                if(e instanceof NoSuchMethodException ||e instanceof IllegalAccessException){
//                    logger.log(Level.SEVERE,"不正常的调用，可能方法名错误或调用私有方法！ " +
//                            "An error occurred in BaseServlet e = {0}", e);
//                }
//                Throwable cause = e.getCause();
//                if (cause instanceof SQLException) {
//                    if (e.getMessage().contains("doesn't exist")) {
//                        // 判断错误信息是否包含数据表不存在异常信息
//                        // 给用户提示操作错误，请联系管理员
//                        resp.getWriter().write("操作错误，请联系管理员！");
//                        resp.setStatus(500);
//                        // 设置状态码为 500
//                    } else {
//                        logger.log(Level.WARNING,"An error occurred in "+cls.getName()+" e = {0}", e.getMessage());
//                    }
//                } else if (cause instanceof IOException) {
//                    // 处理 IO 异常
//                    resp.getWriter().write("系统繁忙，请稍后再试");
//                    logger.log(Level.WARNING,"An error occurred in "+cls.getName()+" e = {0}", e.getMessage());
//                    resp.sendRedirect("http://localhost:8080/project_war/html/error/404.html");
//                } else {
//                    logger.log(Level.WARNING,"An error occurred in "+cls.getName()+" e = {0}", e);
//                    resp.sendRedirect("http://localhost:8080/project_war/html/error/404.html");
//                }
//
//            }catch (IOException ex){
//                // 处理异常时出现异常
//                logger.log(Level.WARNING,"An error occurred in "+cls.getName()+" e = {0}", ex.getMessage());
//            }
//
//        }
//    }
//
//
//
//    protected Object[] convertParams(Method method, HttpServletRequest request) {
//        Parameter[] parameters = method.getParameters();
//        Object[] args = new Object[parameters.length];
//        for (int i = 0; i < parameters.length; i++) {
//            Parameter parameter = parameters[i];
//            String paramName = parameter.getName();
//            Class<?> paramType = parameter.getType();
//            String paramValue = request.getParameter(paramName);
//            if (paramValue != null && !paramValue.isEmpty()) {
//                Object arg = null;
//                if (paramType == String.class) {
//                    arg = paramValue;
//                } else if (paramType == Integer.class || paramType == int.class) {
//                    arg = Integer.parseInt(paramValue);
//                } else if (paramType == Double.class || paramType == double.class) {
//                    arg = Double.parseDouble(paramValue);
//                } else if (paramType == Float.class || paramType == float.class) {
//                    arg = Float.parseFloat(paramValue);
//                } else if (paramType == Date.class) {
//                    arg = parseDate(paramValue);
//                } else {
//                    // 非基本类型，需要进行自定义转换
//                    arg = customConvert(paramValue, paramType);
//                }
//                args[i] = arg;
//            }
//        }
//        return args;
//    }
//
//    /**
//     * 自定义类型转换
//     * @param paramValue 参数值
//     * @param paramType 转换后的类型
//     * @return 返回
//     */
//    private Object customConvert(String paramValue, Class<?> paramType) {
//        //TODO: 自定义转换
//        return null;
//    }
//
//    /**
//     * 解析日期字符串
//     * @param paramValue 参数值
//     * @return 返回
//     */
//    private Date parseDate(String paramValue) {
//        //TODO: 自定义日期
//        return null;
//    }
//}
