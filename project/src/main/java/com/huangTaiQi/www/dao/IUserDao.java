package com.huangTaiQi.www.dao;

import com.huangTaiQi.www.model.entity.UserEntity;

import java.sql.SQLException;
import java.util.List;

/**
 * @author 14629
 */
public interface IUserDao {
    /**
     * 注册用户
     * @param email 注册邮箱
     * @param password 密码
     * @throws SQLException 异常
     */
    void setEmailAndPassword(String email, String password) throws SQLException;

    /**
     * 根据邮箱获取用户
     * @param email 邮箱
     * @return 用户
     * @throws Exception 异常
     */
    UserEntity selectByEmail(String email) throws Exception;

    /**
     * 根据邮箱和密码获取用户
     * @param usernameOrEmail 邮箱
     * @param encode 密码
     * @return 用户
     * @throws Exception 异常
     */
    UserEntity selectByEmailAndPassword(String usernameOrEmail, String encode) throws Exception;

    /**
     * 根据用户名和密码获取用户
     * @param usernameOrEmail 用户名
     * @param encode 密码
     * @return 用户
     * @throws Exception 异常
     */
    UserEntity selectByUsernameAndPassword(String usernameOrEmail, String encode) throws Exception;

    /**
     * 改密码
     * @param email 邮箱
     * @param encode 新密码
     * @throws SQLException 异常
     */
    void alterPassword(String email, String encode) throws SQLException;

    /**
     * 根据用户名模糊查询
     * @param username 用户名
     * @return user
     * @throws Exception 异常
     */
    List<UserEntity> getUserByBlurUsername(String username) throws Exception;

    /**
     * 通过id获取user
     * @param id id
     * @return user
     * @throws Exception 异常
     */
    UserEntity getUserById(String id) throws Exception;

    /**
     * 更新用户的setting
     * @param username 用户名
     * @param gender 性别
     * @param email 邮箱
     * @param introduce 简介
     * @param imgPath 头像
     * @throws SQLException 异常
     */
    void updateUserSettings(String username, String gender, String email, String introduce, String imgPath) throws SQLException;

    /**
     * 通过用户名获取用户
     * @param username 用户名
     * @return 用户
     * @throws Exception 异常
     */
    UserEntity getUserByUsername(String username) throws Exception;

    /**
     * 更新问题数量
     * @param uid uid
     * @param addCount 加的数字
     * @throws SQLException 异常
     */
    void updateQuestionCount(Long uid, int addCount) throws SQLException;

    /**
     * 更新回答数量
     * @param uid uid
     * @param addCount 加的数字
     * @throws SQLException 异常
     */
    void updateAnswerCount(Long uid, int addCount) throws SQLException;

    /**
     * 更新评论数量
     * @param uid uid
     * @param addCount 加的数字
     * @throws SQLException 异常
     */
    void updateCommentCount(Long uid, int addCount) throws SQLException;

    /**
     * 更新粉丝数量
     * @param uid uid
     * @param addCount 加的数字
     * @throws SQLException 异常
     */
    void updateFollowee(Long uid, int addCount) throws SQLException;
}
