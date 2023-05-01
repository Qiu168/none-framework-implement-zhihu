package com.huangTaiQi.www.dao;

import com.huangTaiQi.www.model.entity.CategoryEntity;
import com.huangTaiQi.www.model.entity.QuestionEntity;

import java.util.List;

/**
 * @author 14629
 */
public interface IQuestionDao {
    /**
     * 获取问题的数量
     * @return 数量
     * @throws Exception 异常
     */
    int getQuestionCount() throws Exception;

    /**
     * 根据标题获取问题的数量
     * @param title 标题
     * @return 数量
     * @throws Exception 异常
     */
    int getQuestionCountByTitle(String title) throws Exception;

    /**
     * 根据种类获取问题的数量
     * @param categoryId 种类
     * @return 问题的数量
     * @throws Exception 异常
     */
    int getQuestionCountByCategory(String categoryId) throws Exception;

    /**
     * 获取问题
     * @param page 页数
     * @param size 大小
     * @return 问题
     * @throws Exception 异常
     */
    List<QuestionEntity> getQuestions(int page, int size) throws Exception;

    /**
     * 问题
     * @param page 页数
     * @param size 大小
     * @param title 题目
     * @return 问题
     * @throws Exception 异常
     */
    List<QuestionEntity> getQuestionsByTitle(int page, int size, String title) throws Exception;

    /**
     * 问题
     * @param page 页数
     * @param size 大小
     * @param categoryId 种类的id
     * @return 问题
     * @throws Exception 异常
     */
    List<QuestionEntity> getQuestionsByCategoryId(int page, int size, String categoryId) throws Exception;

    /**
     * 获取所有种类
     * @return 种类
     * @throws Exception 异常
     */
    List<CategoryEntity> getCategory() throws Exception;

    /**
     * 获取某个用户的问题
     * @param page 页数
     * @param size 大小
     * @param userId 用户
     * @return 问题
     * @throws Exception 异常
     */
    List<QuestionEntity> getQuestionByUser(int page, int size, Long userId) throws Exception;
}
