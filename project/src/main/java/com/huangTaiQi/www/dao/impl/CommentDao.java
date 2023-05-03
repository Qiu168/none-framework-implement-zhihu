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

    public CommentEntity getCommentById(String id) throws Exception {
        String sql=new SQLBuilder("comment")
                .select("*")
                .where("id")
                .buildSelect();
        return baseDao.selectOne(sql, CommentEntity.class,id);
    }

    public void addComment(String content, String pid, String tid) throws SQLException {
        String sql=new SQLBuilder("comment")
                .insert("content")
                .insert("pid")
                .insert("top_id")
                .buildInsert();
        baseDao.updateCommon(sql,content,pid,tid);
    }

    public List<CommentEntity> getCommentByAnswerId(String answerId) throws Exception {
        String sql=new SQLBuilder("comment")
                .select("*")
                .where("answer_id")
                .buildSelect();
        return baseDao.selectByParams(sql, CommentEntity.class,answerId);
    }
}
