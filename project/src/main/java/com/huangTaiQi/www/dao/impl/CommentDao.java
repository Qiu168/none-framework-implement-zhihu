package com.huangTaiQi.www.dao.impl;

import com.huangTaiQi.www.dao.BaseDao;
import com.huangTaiQi.www.dao.ICommentDao;
import com.huangTaiQi.www.model.entity.CommentEntity;
import com.huangTaiQi.www.utils.sql.SQLBuilder;
import com.my_framework.www.annotation.Repository;
import com.my_framework.www.pool.DataBaseUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 14629
 */
@Repository
public class CommentDao implements ICommentDao {
    private final Connection connection = DataBaseUtil.getConnection();
    private final BaseDao baseDao=new BaseDao(connection);
    @Override
    public CommentEntity getCommentById(String id) throws Exception {
        String sql=new SQLBuilder("comment")
                .select("*")
                .where("id")
                .buildSelect();
        return baseDao.selectOne(sql, CommentEntity.class,id);
    }
    @Override
    public void addComment(String id,Long userId, String avatar, String username, String content, String pid, String tid, String answerId, long currentTime) throws SQLException {
        String sql=new SQLBuilder("comment")
                .insert("id")
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
        String sql=new SQLBuilder("comment")
                .select("*")
                .where("answer_id")
                .buildSelect();
        return baseDao.selectByParams(sql, CommentEntity.class,answerId);
    }
    @Override
    public List<CommentEntity> getCommentByState(int page, int size, int state) throws Exception {
        String sql=new SQLBuilder("comment")
                .select("*")
                .limit(size)
                .offset((page-1)*size)
                .where("state")
                .buildSelect();
        return baseDao.selectByParams(sql, CommentEntity.class,state);
    }
    @Override
    public void updateCommentState(int state, long commentId) throws SQLException {
        String sql=new SQLBuilder("comment")
                .update(CommentEntity::getState)
                .where("id")
                .buildUpdate();
        baseDao.updateCommon(sql,state,commentId);
    }
    @Override
    public int getCommentCountByState(int state) throws Exception {
        String sql=new SQLBuilder("comment")
                .count("*")
                .where("state")
                .buildSelect();
        return baseDao.selectOne(sql,Integer.class,state);
    }
}
