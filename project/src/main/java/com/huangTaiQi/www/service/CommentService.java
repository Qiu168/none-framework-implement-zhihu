package com.huangTaiQi.www.service;

import com.huangTaiQi.www.model.entity.CommentEntity;

import java.sql.SQLException;

/**
 * @author 14629
 */
public interface CommentService {
    /**
     * 新增评论
     * @param content 内容
     * @param answerId 回答id
     * @param pid 父评论id
     * @throws Exception 异常
     */
    void addComment(String content, String answerId, String pid) throws Exception;

    /**
     * 根据id获取评论
     * @param id id
     * @return 评论
     * @throws Exception 异常
     */
    CommentEntity getCommentById(String id) throws Exception;

    /**
     * 获取评论树
     * @param answerId 回答id
     * @param sortOrder 排序规则
     * @return 评论树
     * @throws Exception 异常
     */
    String getCommentTree(String answerId, String sortOrder) throws Exception;

    /**
     * 获取未审核的评论
     * @param page 页数
     * @param size 大小
     * @return 评论
     * @throws Exception 异常
     */
    String getUncheckedComment(int page, int size) throws Exception;

    /**
     * 审核通过评论
     * @param id id
     * @throws SQLException 异常
     */
    void passComment(String id) throws SQLException;

    /**
     * 根据状态获取评论的数量
     * @param state 状态
     * @return 评论
     * @throws Exception 异常
     */
    int getCommentCountByState(int state) throws Exception;
}
