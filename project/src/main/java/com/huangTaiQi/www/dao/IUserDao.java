package com.huangTaiQi.www.dao;

import com.huangTaiQi.www.model.entity.UserEntity;

public interface IUserDao {
    UserEntity selectByEmailAndPassword(String usernameOrEmail, String encode) throws Exception;

    UserEntity selectByUsernameAndPassword(String usernameOrEmail, String encode) throws Exception;
}
