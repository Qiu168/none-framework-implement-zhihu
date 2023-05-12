package com.huangTaiQi.www.service;

import com.huangTaiQi.www.model.dto.UserDTO;

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

    /**
     * 登录
     * @param usernameOrEmail 用户名或邮箱
     * @param password 密码
     * @param imgCode 图形验证码
     * @param code 验证码
     * @return 信息
     * @throws Exception 异常
     */
    String login(String usernameOrEmail, String password, String imgCode, String code) throws Exception;

    /**
     * 重置密码
     * @param email email
     * @param emailCode 邮箱验证码
     * @param password 重置后的密码
     * @param rePassword 重复输入
     * @return 信息
     * @throws NoSuchAlgorithmException 异常
     * @throws SQLException 异常
     */
    String resetPassword(String email, String emailCode, String password, String rePassword) throws NoSuchAlgorithmException, SQLException;

    String verifyUser(String email, String emailCode, String password, String rePassword);

    /**
     * 根据用户名模糊搜索用户
     * @param username 用户名
     * @return 搜索出的用户集合
     * @throws Exception 异常
     */
    String getUser(String username) throws Exception;

    /**
     * 根据id获取用户
     * @param id id
     * @return 用户
     * @throws Exception 异常
     */
    String getUserById(String id) throws Exception;

    /**
     * 更新用户的设置
     * @param username 用户名
     * @param gender 性别
     * @param email 邮箱
     * @param introduce 简介
     * @param imgPath 头像
     * @throws SQLException 异常
     */
    void updateUserSettings(String username, String gender, String email, String introduce, String imgPath) throws SQLException;

    /**
     * 是否互相关注
     * @param user user
     * @param username 用户名
     * @return 如果互关，返回查询的用户Id，否则返回0
     * @throws Exception 异常
     */
    Long isFollowEachOther(UserDTO user, String username) throws Exception;
}
