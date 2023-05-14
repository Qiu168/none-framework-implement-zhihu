package com.huangTaiQi.www.service;

import java.sql.SQLException;

/**
 * @author 14629
 */
public interface QuestionService {
    /**
     * 获取问题的数量
     * @return 问题的数量
     * @throws Exception 异常
     */
    int getQuestionCount() throws Exception;

    /**
     * 根据标题获取文章的数量
     * @param title 标题
     * @return 返回值
     * @throws Exception 异常
     */
    int getQuestionCountByTitle(String title) throws Exception;

    /**
     * 根据种类获取问题的数量
     * @param categoryId 种类的id
     * @return 数量
     * @throws Exception 异常
     */
    int getQuestionCountByCategory(String categoryId) throws Exception;

    /**
     * 获取问题
     * @param page 页数
     * @param size 获取问题的数量
     * @return json
     * @throws Exception 异常
     */
    String getQuestion(int page, int size) throws Exception;

    /**
     * 根据标题获取问题
     * @param page 页数
     * @param size 每页的问题数
     * @param title 标题
     * @return 返回值
     * @throws Exception 异常
     */

    String getQuestionByTitle(int page, int size, String title) throws Exception;

    /**
     * 根据种类获取问题
     * @param page 页数
     * @param size 每页的问题数
     * @param categoryId 种类的id
     * @return 返回值
     * @throws Exception 异常
     */
    String getQuestionByCategory(int page, int size, String categoryId) throws Exception;

    /**
     * 获取所有种类
     * @return 所有种类
     * @throws Exception 异常
     */
    String getCategory() throws Exception;

    /**
     * 获取该用户的问题
     * @param page 页数
     * @param size 每页的问题数
     * @param userId 用户的id
     * @return 问题
     * @throws Exception 异常
     */
    String getQuestionByUser(int page, int size, Long userId) throws Exception;

    /**
     * 获取问题
     * @param id id
     * @return 问题
     * @throws Exception 异常
     */
    String getQuestionById(String id) throws Exception;

    /**
     * 根据回答获取问题
     * @param answerId 回答id
     * @return 问题
     * @throws Exception 异常
     */
    String getQuestionByAnswerId(String answerId) throws Exception;

    /**
     * 提问
     * @param title 标题
     * @param content 内容
     * @param categoryId 种类id
     * @param categoryName 种类name
     * @return 是否成功
     * @throws SQLException 异常
     */
    String sendQuestion(String title, String content, String categoryId, String categoryName) throws SQLException;

    /**
     * 审核通过问题
     * @param id id
     * @throws SQLException 异常
     */
    void passQuestion(String id) throws SQLException;

    /**
     * 获取未审核的问题
     * @param page 页数
     * @param size 大小
     * @return 问题
     * @throws Exception 异常
     */
    String getUncheckedQuestion(int page, int size) throws Exception;

    /**
     * 根据状态获取问题
     * @param state 状态
     * @return 问题
     * @throws Exception 异常
     */
    int getQuestionCountByState(int state) throws Exception;

    String getReportedQuestion(int page, int size) throws Exception;

    void passReportedQuestion(String questionId, String intentional) throws SQLException;
}
