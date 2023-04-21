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
     * @param email 邮箱
     * @param imgCode 验证码
     * @param request request
     * @param response response
     * @throws IOException 异常
     */
    void sendEmail(String email, String imgCode, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 注册
     * @param email 邮箱
     * @param emailCode 验证码
     * @param password 密码
     * @param rePassword 密码
     * @param response response
     * @throws IOException 异常
     * @throws SQLException 异常
     * @throws NoSuchAlgorithmException 异常
     */
    void register(String email, String emailCode, String password, String rePassword, HttpServletResponse response) throws SQLException, IOException, NoSuchAlgorithmException;
    /**
     * 登录
     * @param usernameOrEmail 用户名或邮箱
     * @param password 密码
     * @param imgCode 验证码
     * @param request request
     * @param response response
     * @throws Exception 异常
     */
    void login(String usernameOrEmail, String password, String imgCode, HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 检查此邮箱是否被注册
     * @param email 邮箱
     * @param request request
     * @param response response
     * @throws Exception 异常
     */
    void checkEmail(String email,HttpServletRequest request, HttpServletResponse response) throws Exception;


    /**
     * 返回现在登录的信息
     * @param request req
     * @param response resp
     * @throws IOException 异常
     */
    void me(HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 重置密码
     * @param email 绑定的邮箱
     * @param emailCode 邮箱验证码
     * @param password 重置后的密码
     * @param rePassword 第二次输入的密码
     * @param response resp
     * @throws SQLException 异常
     * @throws IOException 异常
     * @throws NoSuchAlgorithmException 异常
     */
    void resetPassword( String email, String emailCode, String password, String rePassword, HttpServletResponse response) throws SQLException, IOException, NoSuchAlgorithmException;
}
