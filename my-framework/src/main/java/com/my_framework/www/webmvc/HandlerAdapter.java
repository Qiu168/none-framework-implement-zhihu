package com.my_framework.www.webmvc;



import com.my_framework.www.annotation.RequestParam;
import com.my_framework.www.utils.CastUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author 14629
 */
public class HandlerAdapter {
    private final Logger logger= Logger.getLogger(HandlerAdapter.class.getName());

    private static final HandlerAdapter HANDLER_ADAPTER =new HandlerAdapter();

    private HandlerAdapter() {
    }

    public static HandlerAdapter getAdapter() {
        return HANDLER_ADAPTER;
    }

    public void handle(HttpServletRequest request, HttpServletResponse response, HandlerMapping handlerMapping) {
        //把方法的形参列表和request的参数列表所在顺序进行一一对应
        Map<String, Integer> paramIndexMapping = new HashMap<>();
        //提取方法中加了注解的参数
        //把方法上的注解拿到，得到的是一个二维数组
        //因为一个参数可以有多个注解，而一个方法又有多个参数
        Annotation[][] pa = handlerMapping.getMethod().getParameterAnnotations();
        for (int i = 0; i < pa.length; i++) {
            for (Annotation a : pa[i]) {
                if (a instanceof RequestParam) {
                    String paramName = ((RequestParam) a).value();
                    if (!"".equals(paramName.trim())) {
                        //RequestParam参数不为空，代表要注入
                        paramIndexMapping.put(paramName, i);
                    }
                }
            }
        }

        //提取方法中的request和response参数
        Class<?>[] paramsTypes = handlerMapping.getMethod().getParameterTypes();
        for (int i = 0; i < paramsTypes.length; i++) {
            Class<?> type = paramsTypes[i];
            if (type == HttpServletRequest.class || type == HttpServletResponse.class) {
                paramIndexMapping.put(type.getName(), i);
            }
        }

        //获取提交表单数据
        Map<String, String[]> params = request.getParameterMap();

        //controller的方法实参列表
        Object[] paramValues = new Object[paramsTypes.length];

        for (Map.Entry<String, String[]> param : params.entrySet()) {
            //返回的数组若为[1, 2, 3]，处理后，value 字符串的值将为 "1,2,3"
            String value = Arrays.toString(param.getValue()).replaceAll("\\[|\\]", "")
                    .replaceAll("\\s", ",");

            if (!paramIndexMapping.containsKey(param.getKey())) {
                //被RequestParam注解的参数没有这个param
                continue;
            }
            //参数列表所在顺序
            int index = paramIndexMapping.get(param.getKey());
            //类型转换
            paramValues[index] = parseStringValue(value, paramsTypes[index]);
        }

        //填充HttpServletRequest参数
        if (paramIndexMapping.containsKey(HttpServletRequest.class.getName())) {
            int reqIndex = paramIndexMapping.get(HttpServletRequest.class.getName());
            paramValues[reqIndex] = request;
        }

        //填充HttpServletResponse参数
        if (paramIndexMapping.containsKey(HttpServletResponse.class.getName())) {
            int respIndex = paramIndexMapping.get(HttpServletResponse.class.getName());
            paramValues[respIndex] = response;
        }

        //反射调用controller的方法
        try {
            handlerMapping.getMethod().invoke(handlerMapping.getController(), paramValues);
        } catch (Exception e) {
            Class<?> cls = handlerMapping.getController().getClass();
            handleException(e,cls,response);
        }
    }

    private void handleException(Exception e, Class<?> cls, HttpServletResponse response) {
        try {
            if(e instanceof NoSuchMethodException ||e instanceof IllegalAccessException){
                logger.log(Level.SEVERE,"不正常的调用，可能方法名错误或调用私有方法！ " +
                        "An error occurred in BaseServlet e = {0}", e);
            }
            Throwable cause = e.getCause();
            if (cause instanceof SQLException) {
                if (e.getMessage().contains("doesn't exist")) {
                    // 判断错误信息是否包含数据表不存在异常信息
                    // 给用户提示操作错误，请联系管理员
                    response.getWriter().write("操作错误，请联系管理员！");
                    response.setStatus(500);
                    // 设置状态码为 500
                } else {
                    logger.log(Level.WARNING,"An error occurred in "+cls.getName()+" e = {0}", e.getMessage());
                }
            } else if (cause instanceof IOException) {
                // 处理 IO 异常
                response.getWriter().write("系统繁忙，请稍后再试");
                logger.log(Level.WARNING,"An error occurred in "+cls.getName()+" e = {0}", e.getMessage());
                response.sendRedirect("http://localhost:8080/project_war/html/error/404.html");
            } else {
                logger.log(Level.WARNING,"An error occurred in "+cls.getName()+" e = {0}", e);
                response.sendRedirect("http://localhost:8080/project_war/html/error/404.html");
            }
        }catch (IOException ex){
            // 处理异常时出现异常
            logger.log(Level.WARNING,"An error occurred in "+cls.getName()+" e = {0}", ex.getMessage());
        }
    }

    /**
     * request中接收的参数都是string类型的，需要转换为controller中实际的参数类型
     * 暂时只支持string、int、double类型
     */
    private Object parseStringValue(String value, Class<?> paramsType) {
        if (String.class == paramsType) {
            return value;
        }else if (int.class == paramsType) {
            return CastUtil.castInt(value);
        } else if (Double.class == paramsType) {
            return CastUtil.castDouble(value);
        } else if (Long.class==paramsType) {
            return CastUtil.castLong(value);
        } else if (Boolean.class==paramsType) {
            return CastUtil.castBoolean(value);
        } else {
            return value;
        }
    }
}
