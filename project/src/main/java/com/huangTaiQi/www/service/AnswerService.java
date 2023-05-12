package com.huangTaiQi.www.service;

import java.sql.SQLException;

/**
 * @author 14629
 */
public interface AnswerService {
    /**
     * 获取用户回答
     * @param userId uid
     * @return 回答
     * @throws Exception 异常
     */
    String getUserAnswer(String userId) throws Exception;

    /**
     * 获取回答根据问题
     * @param questionId 问题
     * @return 回答
     * @throws Exception 异常
     */
    String getAnswerByQuestionId(String questionId) throws Exception;

    /**
     * 获取回答
     * @param id id
     * @return 回答
     * @throws Exception 异常
     */
    String getAnswerById(String id) throws Exception;

    /**
     * 新增回答
     * @param questionId 问题id
     * @param title 标题
     * @param content 内容
     * @return 回答
     * @throws SQLException 异常
     */
    String addAnswer(String questionId, String title, String content) throws SQLException;

    /**
     * 审核通过回答
     * @param id id
     * @throws SQLException 异常
     */
    void passAnswer(String id) throws Exception;

    /**
     * 获取没有审核的回答
     * @param page 页数
     * @param size 大小
     * @return 回答
     * @throws Exception 异常
     */
    String getUncheckedAnswer(int page, int size) throws Exception;

    /**
     * 根据状态获取数量
     * @param state 状态
     * @return 数量
     * @throws Exception 异常
     */
    int getAnswerCountByState(int state) throws Exception;
}
