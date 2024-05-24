package com.huangTaiQi.www.controller;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author _qqiu
 */
public interface IQuestionController {

    /**
     * 获取问题的数量
     * @param response resp
     * @throws Exception 异常
     */
    void getQuestionCount(HttpServletResponse response) throws Exception;

    /**
     * 根据Title获取Question的数量
     * @param title 标题
     * @param response resp
     * @throws Exception 异常
     */
    void getQuestionCountByTitle(String title, HttpServletResponse response) throws Exception;

    /**
     * 根据种类获取问题数量
     * @param categoryId 种类id
     * @param response resp
     * @throws Exception 异常
     */
    void getQuestionCountByCategory(String categoryId, HttpServletResponse response) throws Exception;

    /**
     * 获取问题
     * @param page 页数
     * @param size 大小
     * @param response resp
     * @throws Exception 异常
     */
    void getQuestion(int page, int size, HttpServletResponse response) throws Exception;

    /**
     * 根据标题获取问题
     * @param title 标题
     * @param page 页数
     * @param size 大小
     * @param response resp
     * @throws Exception 异常
     */
    void getQuestionByTitle( String title, int page,int size,HttpServletResponse response) throws Exception;

    /**
     * 根据种类获取问题
     * @param categoryId 种类id
     * @param page 页数
     * @param size 大小
     * @param response resp
     * @throws Exception 异常
     */
    void getQuestionByCategory( String categoryId,int page,int size, HttpServletResponse response) throws Exception;

    /**
     * 根据用户获取问题
     * @param userId 用户id
     * @param page 页数
     * @param size 大小
     * @param response resp
     * @throws Exception 异常
     */
    void getQuestionByUser(Long userId, int page, int size, HttpServletResponse response) throws Exception;

    /**
     * 获取种类
     * @param response resp
     * @throws Exception 异常
     */
    void getCategory(HttpServletResponse response) throws Exception;

    /**
     * 根据id获取问题
     * @param id id
     * @param response resp
     * @throws Exception 异常
     */
    void getQuestionById(String id, HttpServletResponse response) throws Exception;

    /**
     * 通过回答找问题
     * @param answerId 回答id
     * @param response resp
     * @throws Exception 异常
     */
    void getQuestionByAnswerId( String answerId, HttpServletResponse response) throws Exception;

    /**
     * 发送问题
     * @param title 标题
     * @param categoryId 种类
     * @param categoryName 种类名称
     * @param content 内容
     * @param response resp
     * @throws SQLException 异常
     * @throws IOException 异常
     */
    void sendQuestion(String title, String categoryId, String categoryName, String content, HttpServletResponse response) throws SQLException, IOException;

    /**
     * 审核通过问题
     * @param questionId 问题id
     * @param id uid
     * @throws Exception 异常
     */
    void passQuestion(String questionId, Long id) throws Exception;

    /**
     * 获取没有审核的问题
     * @param page 页数
     * @param size 大小
     * @param response resp
     * @throws Exception 异常
     */
    void getUncheckedQuestion(int page, int size, HttpServletResponse response) throws Exception;

    /**
     * 获取未审核的问题数量
     * @param response resp
     * @throws Exception 异常
     */
    void getUncheckedTotal(HttpServletResponse response) throws Exception;

    /**
     * 获取被举报的问题
     * @param page 页数
     * @param size 大小
     * @param response resp
     * @throws Exception 异常
     */
    void getReportedQuestion(int page,int size, HttpServletResponse response) throws Exception;

    /**
     * 获取被举报的总书
     * @param response resp
     * @throws Exception 异常
     */
    void getReportedTotal(HttpServletResponse response) throws Exception;

    /**
     * 通过
     * @param questionId id
     * @param id uid
     * @param intentional 是否恶意
     * @throws SQLException 异常
     */
    void passReported(String questionId, Long id,String intentional) throws SQLException;
}
