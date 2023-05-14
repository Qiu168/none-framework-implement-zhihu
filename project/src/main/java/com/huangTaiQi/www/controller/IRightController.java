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


    void getRightByUsername(String username, HttpServletResponse response) throws Exception;


    void banUser( String username, HttpServletResponse response) throws Exception;
}
