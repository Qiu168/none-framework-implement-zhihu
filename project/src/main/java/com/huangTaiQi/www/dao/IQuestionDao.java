package com.huangTaiQi.www.dao;

import com.huangTaiQi.www.model.entity.CategoryEntity;
import com.huangTaiQi.www.model.entity.QuestionEntity;

import java.sql.SQLException;
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

    /**
     * 根据id获取问题
     * @param id id
     * @return 问题
     * @throws Exception 异常
     */
    QuestionEntity getQuestionById(String id) throws Exception;

    /**
     * 新增问题
     * @param title 标题
     * @param content 内容
     * @param categoryId 种类id
     * @param categoryName 种类name
     * @param avatar 头像
     * @param username 用户名
     * @param uid uid
     * @throws SQLException 异常
     */
    void addQuestion(String title, String content, String categoryId, String categoryName, String avatar, String username, Long uid) throws SQLException;

    /**
     * 更新状态
     * @param state 状态
     * @param id id
     * @throws SQLException 异常
     */
    void updateQuestionState(int state, Long id) throws SQLException;

    /**
     * 根据ids获取问题
     * @param ids ids
     * @return 问题
     * @throws Exception 异常
     */
    List<QuestionEntity> getQuestionByIds(List<Long> ids) throws Exception;

    /**
     * 根据state获取问题
     * @param page 页数
     * @param size 大小
     * @param state 状态
     * @return 问题
     * @throws Exception 异常
     */
    List<QuestionEntity> getQuestionByState(int page, int size, int state) throws Exception;

    /**
     * 根据状态获取问题数量
     * @param state 状态
     * @return 问题
     * @throws Exception 异常
     */
    int getQuestionCountByState(int state) throws Exception;
}
