package com.huangTaiQi.www.filter;


import com.huangTaiQi.www.utils.UserHolder;


import javax.servlet.FilterChain;

import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;


/**
 * @author 14629
 */
//@WebFilter("/api/user/*")
public class LoginFilter extends BaseFilter{
    @Override
    protected boolean doBeforeProcessing(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws Exception {
        //TODO:放行不用注册的页面
        //忽略请求地址
        List<String> ignoreUrl
                = Arrays.asList(
                "/adminService/auth/token",
                "/adminService/auth/refreshToken");
        //判断是否需要拦截（ThreadLocal中是否有用户）
        if (UserHolder.getUser() == null) {
            // 没有，需要拦截，设置状态码
            response.setStatus(401);
            // 拦截
            response.sendRedirect("http://localhost:8080/project_war_exploded/html/error/404.html");
            return false;
        }
        // 有用户，则放行
        return true;
    }
}
