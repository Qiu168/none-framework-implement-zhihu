package com.huangTaiQi.www.dao;

import com.huangTaiQi.www.model.entity.UserEntity;

import java.sql.SQLException;

public interface IUserDao {
    void setEmailAndPassword(String email, String password) throws SQLException;
    UserEntity selectByEmail(String email) throws Exception;
    UserEntity selectByEmailAndPassword(String usernameOrEmail, String encode) throws Exception;

    UserEntity selectByUsernameAndPassword(String usernameOrEmail, String encode) throws Exception;

    void alterPassword(String email, String encode) throws SQLException;
}
