package com.huangTaiQi.www.dao;

import com.huangTaiQi.www.model.entity.CommentEntity;

import java.sql.SQLException;
import java.util.List;

/**
 * @author 14629
 */
public interface ICommentDao {
    /**
     * 获取评论
     * @param id id
     * @return 评论
     * @throws Exception 异常
     */
    CommentEntity getCommentById(String id) throws Exception;

    /**
     * 新增评论
     * @param id id
     * @param userId uid
     * @param avatar 头像
     * @param username 用户名
     * @param content 内容
     * @param pid 父评论id
     * @param tid 一级评论id
     * @param answerId 回答id
     * @param currentTime 当前时间
     * @throws SQLException 异常
     */
    void addComment(String id, Long userId, String avatar, String username, String content, String pid, String tid, String answerId, long currentTime) throws SQLException;

    /**
     * 通过回答获取评论
     * @param answerId 回答
     * @return 评论
     * @throws Exception 异常
     */
    List<CommentEntity> getCommentByAnswerId(String answerId) throws Exception;

    /**
     * 通过state获取评论
     * @param page 页数
     * @param size 大小
     * @param state 状态
     * @return 评论
     * @throws Exception 异常
     */
    List<CommentEntity> getCommentByState(int page, int size, int state) throws Exception;

    /**
     * 更新comment的状态
     * @param state 状态
     * @param commentId 评论id
     * @throws SQLException 异常
     */
    void updateCommentState(int state, String commentId) throws SQLException;

    /**
     * 根据state获取评论的数量
     * @param state 状态
     * @return 数量
     * @throws Exception 异常
     */
    int getCommentCountByState(int state) throws Exception;
}
