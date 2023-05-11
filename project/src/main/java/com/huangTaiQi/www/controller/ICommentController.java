package com.huangTaiQi.www.controller;


import javax.servlet.http.HttpServletResponse;

/**
 * @author 14629
 */
public interface ICommentController {
    /**
     * 发评论
     * @param content 内容
     * @param answerId 回答id
     * @param pid 父评论id
     * @throws Exception 异常
     */
    void sendComment( String content, String answerId, String pid) throws Exception;

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
}
