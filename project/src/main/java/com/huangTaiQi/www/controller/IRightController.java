package com.huangTaiQi.www.controller;


import javax.servlet.http.HttpServletResponse;

/**
 * @author 14629
 */
public interface IRightController {
    /**
     * 禁用user的权限
     * @param right 权限
     * @param banTime 禁用时间
     */
    void banRight(String username, Integer right, Long banTime, HttpServletResponse response) throws Exception;

    /**
     * 通过username获取权限
     * @param username 用户名
     * @param response resp
     * @throws Exception 异常
     */
    void getRightByUsername(String username, HttpServletResponse response) throws Exception;

    /**
     * 禁用user
     * @param username 用户名
     * @param response resp
     * @throws Exception
     */
    void banUser( String username, HttpServletResponse response) throws Exception;
}
