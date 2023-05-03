package com.huangTaiQi.www.service;

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
}
