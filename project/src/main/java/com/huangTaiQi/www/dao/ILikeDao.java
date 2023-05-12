package com.huangTaiQi.www.dao;

import com.huangTaiQi.www.model.entity.LikeEntity;

import java.sql.SQLException;

/**
 * @author 14629
 */
public interface ILikeDao {
    /**
     * 查询是否点赞
     * @param userId uid
     * @param answerId aid
     * @return 点赞
     * @throws Exception 异常
     */
    LikeEntity selectLike(Long userId, String answerId) throws Exception;

    /**
     * 点赞
     * @param id uid
     * @param answerId aid
     * @throws SQLException 异常
     */
    void addLike(Long id, String answerId) throws SQLException;

    /**
     * 取消点赞
     * @param id uid
     * @param answerId aid
     * @throws SQLException 异常
     */
    void deleteLike(Long id, String answerId) throws SQLException;
}
