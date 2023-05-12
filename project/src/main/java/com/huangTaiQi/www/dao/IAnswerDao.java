package com.huangTaiQi.www.dao;

import com.huangTaiQi.www.model.entity.AnswerEntity;
import com.huangTaiQi.www.model.vo.QuestionAnswer;

import java.sql.SQLException;
import java.util.List;

/**
 * @author 14629
 */
public interface IAnswerDao {
    /**
     * 根据用户获取回答
     * @param userId 用户
     * @return 回答和问题的标题
     * @throws Exception 异常
     */
    List<QuestionAnswer> getUserAnswer(String userId) throws Exception;

    /**
     * 根据问题获取回答
     * @param questionId 问题
     * @return 回答
     * @throws Exception 异常
     */
    List<AnswerEntity> getAnswerByQuestionId(String questionId) throws Exception;

    /**
     * 获取回答
     * @param id 回答id
     * @return 回答
     * @throws Exception 异常
     */
    AnswerEntity getAnswerById(String id) throws Exception;

    /**
     * 更新回答的点赞数
     * @param answerId 回答id
     * @param i 数量
     * @throws SQLException 异常
     */
    void updateLikes(String answerId, int i) throws SQLException;

    /**
     * 新增回答
     * @param id id
     * @param avatar 头像
     * @param username 用户名
     * @param title 标题
     * @param content 内容
     * @param questionId 问题
     * @throws SQLException 异常
     */
    void addAnswer(Long id, String avatar, String username, String title, String content, String questionId) throws SQLException;

    /**
     * 更新回答的状态
     * @param state 状态
     * @param id id
     * @throws SQLException 异常
     */
    void updateAnswerState(int state, long id) throws SQLException;

    /**
     * 获取Answer
     * @param ids ids
     * @return 回答
     * @throws Exception 异常
     */
    List<AnswerEntity> getAnswerByIds(List<Long> ids) throws Exception;

    /**
     * 根据state获取回答
     * @param page 页数
     * @param size 大小
     * @param state 状态
     * @return 回答
     * @throws Exception 异常
     */
    List<AnswerEntity> getAnswerByState(int page, int size, int state) throws Exception;

    /**
     * 根据状态获取回答的数量
     * @param state 状态
     * @return 数量
     * @throws Exception 异常
     */
    int getAnswerCountByState(int state) throws Exception;
}
