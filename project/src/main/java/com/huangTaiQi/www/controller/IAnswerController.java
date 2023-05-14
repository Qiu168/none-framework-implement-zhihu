package com.huangTaiQi.www.controller;



import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author 14629
 */
public interface IAnswerController {
    /**
     * 根据用户获取回答
     * @param userId userId
     * @param response resp
     * @throws Exception 异常
     */
    void getUserAnswer(String userId, HttpServletResponse response) throws Exception;

    /**
     * 根据问题获取回答
     * @param questionId 问题id
     * @param response resp
     * @throws Exception 异常
     */
    void getAnswerByQuestionId(String questionId, HttpServletResponse response) throws Exception;


    void getAnswerByQuestionIdAndPage(String questionId, int page, int size, HttpServletResponse response) throws Exception;

    /**
     * 根据id获取回答
     * @param id id
     * @param response resp
     * @throws Exception 异常
     */
    void getAnswerById(String id, HttpServletResponse response) throws Exception;

    /**
     * 回答问题
     * @param title 标题
     * @param content 内容
     * @param questionId 回答的问题id
     * @param response resp
     * @throws SQLException 异常
     * @throws IOException 异常
     */
    void sendAnswer(String title, String content, String questionId, HttpServletResponse response) throws Exception;

    /**
     * 审核通过回答
     * @param answerId 回答
     * @param id uid
     * @throws Exception 异常
     */
    void passAnswer(String answerId, Long id) throws Exception;

    /**
     * 获取没有审核的问题
     * @param page 页数
     * @param size 大小
     * @param response resp
     * @throws Exception 异常
     */
    void getUncheckedAnswer(int page, int size, HttpServletResponse response) throws Exception;

    /**
     * 获取没有审核的问题的数量
     * @param response resp
     * @throws Exception 异常
     */
    void getUncheckedTotal(HttpServletResponse response) throws Exception;


    void getReportedAnswer( int page, int size, HttpServletResponse response) throws Exception;


    void getReportedTotal(HttpServletResponse response) throws Exception;


    void passReported( String answerId, Long id, String intentional) throws SQLException;
}
