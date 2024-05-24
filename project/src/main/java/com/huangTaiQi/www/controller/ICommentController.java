package com.huangTaiQi.www.controller;


import javax.servlet.http.HttpServletResponse;

import java.sql.SQLException;
/**
 * @author _qqiu
 */
public interface ICommentController {
    /**
     * 发评论
     * @param content 内容
     * @param answerId 回答id
     * @param pid 父评论id
     * @param response response
     * @throws Exception 异常
     */
    void sendComment( String content, String answerId, String pid,HttpServletResponse response) throws Exception;

    /**
     * 获取comment
     * @param answerId 回答id
     * @param sortOrder 排序规则
     * @param response resp
     * @throws Exception 异常
     */
    void getComment(String answerId, String sortOrder, HttpServletResponse response) throws Exception;

    /**
     * 获取未审核的comment
     * @param page 页数
     * @param size 大小
     * @param response resp
     * @throws Exception 异常
     */
    void getUncheckedComment(int page, int size, HttpServletResponse response) throws Exception;

    /**
     * 审核通过评论
     * @param id id
     * @param uid 用户id
     * @throws Exception 异常
     */
    void passComment( String id, Long uid) throws Exception;

    /**
     * 获取未审核的comment的数量
     * @param response resp
     * @throws Exception 异常
     */
    void getUncheckedTotal(HttpServletResponse response) throws Exception;

    /**
     * 根据id获取评论
     * @param id id
     * @param response resp
     * @throws Exception 异常
     */
    void getCommentById(String id,HttpServletResponse response) throws Exception;

    /**
     * 获取被举报的评论
     * @param page 页数
     * @param size 大小
     * @param response resp
     * @throws Exception 异常
     */
    void getReportedComment( int page,int size, HttpServletResponse response) throws Exception;

    /**
     * 获取被举报的总数
     * @param response resp
     * @throws Exception 异常
     */
    void getReportedTotal(HttpServletResponse response) throws Exception;

    /**
     * 通过
     * @param commentId id
     * @param id uid
     * @param intentional 是否恶意
     * @throws SQLException 异常
     */
    void passReported( String commentId,Long id,String intentional) throws SQLException;
}
