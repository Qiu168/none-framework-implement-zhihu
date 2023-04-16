package com.huangTaiQi.www.dao.impl;

import com.huangTaiQi.www.dao.BaseDao;
import com.huangTaiQi.www.dao.IUserDao;
import com.huangTaiQi.www.model.entity.UserEntity;
import com.huangTaiQi.www.utils.DataBaseUtil;
import com.huangTaiQi.www.utils.RandomUtils;
import com.huangTaiQi.www.utils.sql.SQLBuilder;
import com.my_framework.www.annotation.Repository;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 14629
 */
@Repository
public class UserDao implements IUserDao {
    private final Connection connection = DataBaseUtil.getConnection();
    BaseDao baseDao=new BaseDao(connection);

    public void setEmailAndPassword(String email, String password) throws SQLException {
        String sql=new SQLBuilder("user").insert("email").insert("password")
                .insert("username").buildInsert();
        baseDao.updateCommon(sql,email,password,"user_"+ RandomUtils.getRandomString(6));
    }

    public UserEntity selectByEmail(String email) throws Exception {
        String sql=new SQLBuilder("user").select("*").where("email").buildSelect();
        List<UserEntity> userEntities = baseDao.selectByParams(sql, UserEntity.class, email);
        if(userEntities==null||userEntities.isEmpty()){
            return null;
        }else{
            return userEntities.get(0);
        }
    }

    @Override
    public UserEntity selectByEmailAndPassword(String email, String encode) throws Exception {
        String sql=new SQLBuilder("user")
                .select("*")
                .where("email")
                .where("password")
                .buildSelect();
        List<UserEntity> userEntities = baseDao.selectByParams(sql, UserEntity.class, email,encode);
        if(userEntities==null||userEntities.isEmpty()){
            return null;
        }else{
            return userEntities.get(0);
        }
    }

    @Override
    public UserEntity selectByUsernameAndPassword(String username, String encode) throws Exception {
        String sql=new SQLBuilder("user")
                .select("*")
                .where("username")
                .where("password")
                .buildSelect();
        List<UserEntity> userEntities = baseDao.selectByParams(sql, UserEntity.class, username,encode);
        if(userEntities==null||userEntities.isEmpty()){
            return null;
        }else{
            return userEntities.get(0);
        }
    }
}
