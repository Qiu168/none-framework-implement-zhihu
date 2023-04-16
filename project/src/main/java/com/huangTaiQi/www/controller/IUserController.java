package com.huangTaiQi.www.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/**
 * @author 14629
 */
public interface IUserController {

    /**
     * * 点击图片时刷新验证码
     * @param request request
     * @param response response
     * @throws IOException 异常
     */
    void getImgVerifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 发送邮箱验证码
     * @param request request
     * @param response response
     * @throws IOException 异常
     */
    void sendEmail(HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 注册
     * @param request request
     * @param response response
     * @throws IOException 异常
     * @throws SQLException 异常
     * @throws NoSuchAlgorithmException 异常
     */
    void register(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, NoSuchAlgorithmException;

    /**
     * 登录
     * @param request request
     * @param response response
     * @throws Exception 异常
     */
    void login(HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 检查此邮箱是否被注册
     * @param request request
     * @param response response
     * @throws Exception 异常
     */
    void checkEmail(HttpServletRequest request, HttpServletResponse response) throws Exception;


    /**
     * 返回现在登录的信息
     * @param request req
     * @param response resp
     * @throws IOException 异常
     */
    void me(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
