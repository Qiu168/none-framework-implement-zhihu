package com.huangTaiQi.www.service;

import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/**
 * @author 14629
 */
public interface UserService {
    /**
     * 生成验证码
     * @param session 存入session
     * @return 返回验证码流
     */
    BufferedImage imgCode(HttpSession session);

    /**
     * 发送邮件验证码
     * @param email 邮箱
     * @param imgCode 验证码
     * @param code session中的验证码
     * @return 返回信息
     */
    String sendEmail(String email,String imgCode,String code);

    /**
     * 检查此邮箱是否被注册
     * @param email email
     * @return 返回此邮箱是否已被注册
     * @throws Exception 异常
     */

    boolean hasEmail(String email) throws Exception;

    /**
     * 注册账号
     * @param email 邮箱
     * @param emailCode 邮箱验证码
     * @param password 密码
     * @param rePassword 重复密码
     * @return 信息
     * @throws SQLException 异常
     * @throws NoSuchAlgorithmException 异常
     */
    String register(String email, String emailCode, String password, String rePassword) throws SQLException, NoSuchAlgorithmException;

    String login(String usernameOrEmail, String password, String imgCode, String code) throws Exception;
}
