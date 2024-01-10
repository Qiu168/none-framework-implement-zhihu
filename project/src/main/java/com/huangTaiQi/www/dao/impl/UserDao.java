package com.huangTaiQi.www.dao.impl;

import com.my_framework.www.db.orm.BaseDao;
import com.huangTaiQi.www.dao.IUserDao;
import com.huangTaiQi.www.model.entity.UserEntity;
import com.huangTaiQi.www.utils.UserHolder;
import com.my_framework.www.db.pool.DataBaseUtil;
import com.huangTaiQi.www.utils.RandomUtils;
import com.huangTaiQi.www.utils.sql.SQLBuilder;
import com.my_framework.www.core.annotation.stereotype.Repository;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.huangTaiQi.www.constant.EntityAttributeConstants.ID;

/**
 * @author 14629
 */
@Repository
public class UserDao implements IUserDao {
    private final Connection connection = DataBaseUtil.getConnection();
    private final BaseDao baseDao=new BaseDao(connection);

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
                .update(UserEntity::getPassword)
                .where("email")
                .buildUpdate();
        baseDao.updateCommon(sql,encode,email);
    }
    @Override
    public List<UserEntity> getUserByBlurUsername(String username) throws Exception {
        String sql=new SQLBuilder("user")
                .select("*")
                .blurWhere("username")
                .buildSelect();
        return baseDao.selectByParams(sql,UserEntity.class,"%"+username+"%");
    }
    @Override
    public UserEntity getUserById(String id) throws Exception {
        String sql=new SQLBuilder("user")
                .select("*")
                .where(ID)
                .buildSelect();
        return baseDao.selectOne(sql, UserEntity.class,id);
    }
    @Override
    public void updateUserSettings(String username, String gender, String email, String introduce, String imgPath) throws SQLException {
        String sql=new SQLBuilder("user")
                .update(UserEntity::getUsername)
                .update(UserEntity::getGender)
                .update(UserEntity::getEmail)
                .update(UserEntity::getIntroduce)
                .update(UserEntity::getAvatar)
                .where(ID)
                .buildUpdate();
        baseDao.updateCommon(sql,username,gender,email,introduce,imgPath, UserHolder.getUser().getId());
    }
    @Override
    public UserEntity getUserByUsername(String username) throws Exception {
        String sql=new SQLBuilder("user")
                .select("*")
                .where("username")
                .buildSelect();
        return baseDao.selectOne(sql,UserEntity.class,username);
    }

    @Override
    public void updateQuestionCount(Long uid, int addCount) throws SQLException {
        String sql="UPDATE user SET question_count = question_count + "+addCount+" WHERE id = ?";
        baseDao.updateCommon(sql,uid);
    }
    @Override
    public void updateAnswerCount(Long uid, int addCount) throws SQLException {
        String sql="UPDATE user SET answer_count = answer_count + "+addCount+" WHERE id = ?";
        baseDao.updateCommon(sql,uid);
    }
    @Override
    public void updateCommentCount(Long uid, int addCount) throws SQLException {
        String sql="UPDATE user SET comment_count = comment_count + "+addCount+" WHERE id = ?";
        baseDao.updateCommon(sql,uid);
    }
    @Override
    public void updateFollowee(Long uid, int addCount) throws SQLException {
        String sql="UPDATE user SET followee = followee + "+addCount+" WHERE id = ?";
        baseDao.updateCommon(sql,uid);
    }

    public void updateRole(Long uid, int role) throws SQLException {
        String sql=new SQLBuilder("user")
                .update(UserEntity::getRoleId)
                .where(ID)
                .buildUpdate();
        baseDao.updateCommon(sql,role,uid);
    }

    public void updateFans(Long uid, int addCount) throws SQLException {
        String sql="UPDATE user SET fans = fans + "+addCount+" WHERE id = ?";
        baseDao.updateCommon(sql,uid);
    }
}
