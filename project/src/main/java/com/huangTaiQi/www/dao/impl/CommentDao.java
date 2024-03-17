package com.huangTaiQi.www.dao.impl;

import com.huangTaiQi.www.dao.*;
import com.huangTaiQi.www.model.UserSettings;
import com.huangTaiQi.www.model.entity.AnswerEntity;
import com.huangTaiQi.www.model.entity.CommentEntity;
import com.my_framework.www.db.orm.sql.SQLBuilder;
import com.my_framework.www.core.annotation.stereotype.Repository;
import com.my_framework.www.db.orm.BaseDao;
import com.my_framework.www.db.pool.DataBaseUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.huangTaiQi.www.constant.EntityAttributeConstants.ID;
import static com.huangTaiQi.www.constant.EntityAttributeConstants.STATE;
import static com.huangTaiQi.www.constant.StateConstants.MESSAGE_CHECKED;
import static com.huangTaiQi.www.constant.StateConstants.MESSAGE_REPORTED;
import static com.huangTaiQi.www.constant.TypeConstants.COMMENT;

/**
 * @author 14629
 */
@Repository
public class CommentDao implements ICommentDao , ReportAble , UpdateUserSettings , SelectById {
    private final Connection connection = DataBaseUtil.getConnection();
    private final BaseDao baseDao=new BaseDao(connection);
    @Override
    public CommentEntity getCommentById(String id) throws Exception {
        String sql=new SQLBuilder(COMMENT)
                .select("*")
                .where(ID)
                .buildSelect();
        return baseDao.selectOne(sql, CommentEntity.class,id);
    }
    @Override
    public void addComment(String id,Long userId, String avatar, String username, String content, String pid, String tid, String answerId, long currentTime) throws SQLException {
        String sql=new SQLBuilder(COMMENT)
                .insert(ID)
                .insert("user_id")
                .insert("avatar")
                .insert("username")
                .insert("content")
                .insert("pid")
                .insert("top_id")
                .insert("answer_id")
                .insert("comment_time")
                .buildInsert();
        baseDao.updateCommon(sql,id,userId,avatar,username,content,pid,tid,answerId,currentTime);
    }
    @Override
    public List<CommentEntity> getCommentByAnswerId(String answerId) throws Exception {
        String sql=new SQLBuilder(COMMENT)
                .select("*")
                .where("answer_id")
                .where(STATE)
                .buildSelect();
        return baseDao.selectByParams(sql, CommentEntity.class,answerId,MESSAGE_CHECKED);
    }
    @Override
    public List<CommentEntity> getCommentByState(int page, int size, int state) throws Exception {
        String sql=new SQLBuilder(COMMENT)
                .select("*")
                .limit(size)
                .offset((page-1)*size)
                .where(STATE)
                .buildSelect();
        return baseDao.selectByParams(sql, CommentEntity.class,state);
    }
    @Override
    public void updateCommentState(int state, String commentId) throws SQLException {
        String sql=new SQLBuilder(COMMENT)
                .update(CommentEntity::getState)
                .where(ID)
                .buildUpdate();
        baseDao.updateCommon(sql,state,commentId);
    }
    @Override
    public int getCommentCountByState(int state) throws Exception {
        String sql=new SQLBuilder(COMMENT)
                .count("*")
                .where(STATE)
                .buildSelect();
        return baseDao.selectOne(sql,Integer.class,state);
    }

    @Override
    public void report(String messageId, Long reporterId) throws SQLException {
        String sql=new SQLBuilder(COMMENT)
                .update(CommentEntity::getState)
                .where(ID)
                .buildUpdate();
        baseDao.updateCommon(sql,MESSAGE_REPORTED,messageId);
    }

    @Override
    public void updateSettings(Long id, String avatar, String username) throws SQLException {
        String sql=new SQLBuilder(COMMENT)
                .update(AnswerEntity::getAvatar)
                .update(AnswerEntity::getUsername)
                .where("user_id")
                .buildUpdate();
        baseDao.updateCommon(sql,avatar,username,id);
    }

    @Override
    public UserSettings selectById(String id) throws Exception {
        return getCommentById(id);
    }
}
