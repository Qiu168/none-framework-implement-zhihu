package com.huangTaiQi.www.dao.impl;

import com.huangTaiQi.www.dao.BaseDao;
import com.huangTaiQi.www.dao.IUserDao;
import com.huangTaiQi.www.model.entity.UserEntity;
import com.my_framework.www.pool.DataBaseUtil;
import com.huangTaiQi.www.utils.RandomUtils;
import com.huangTaiQi.www.utils.sql.SQLBuilder;
import com.my_framework.www.annotation.Repository;


import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author 14629
 */
@Repository
public class UserDao implements IUserDao {
    private final Connection connection = DataBaseUtil.getConnection();
    BaseDao baseDao=new BaseDao(connection);

    @Override
    public void setEmailAndPassword(String email, String password) throws SQLException {
        String sql=new SQLBuilder("user")
                .insert("email")
                .insert("password")
                .insert("username")
                .buildInsert();
        baseDao.updateCommon(sql,email,password,"user_"+ RandomUtils.getRandomString(6));
    }

    @Override
    public UserEntity selectByEmail(String email) throws Exception {
        String sql=new SQLBuilder("user")
                .select("*")
                .where("email")
                .buildSelect();
        return baseDao.selectOne(sql, UserEntity.class, email);
    }

    @Override
    public UserEntity selectByEmailAndPassword(String email, String encode) throws Exception {
        String sql=new SQLBuilder("user")
                .select("*")
                .where("email")
                .where("password")
                .buildSelect();
        return baseDao.selectOne(sql, UserEntity.class, email,encode);
    }

    @Override
    public UserEntity selectByUsernameAndPassword(String username, String encode) throws Exception {
        String sql=new SQLBuilder("user")
                .select("*")
                .where("username")
                .where("password")
                .buildSelect();
        return baseDao.selectOne(sql, UserEntity.class, username,encode);
    }

    @Override
    public void alterPassword(String email, String encode) throws SQLException {
        String sql=new SQLBuilder("user")
                .update("password")
                .where("email")
                .buildUpdate();
        baseDao.updateCommon(sql,encode,email);
    }
}
