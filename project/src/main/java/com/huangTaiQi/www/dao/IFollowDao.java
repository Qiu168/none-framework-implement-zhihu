package com.huangTaiQi.www.dao;

import com.huangTaiQi.www.model.entity.FollowEntity;

import java.sql.SQLException;
import java.util.List;

/**
 * @author 14629
 */
public interface IFollowDao {
    /**
     * 共同关注
     * @param userId uid
     * @param followeeId fid
     * @return 共同关注
     * @throws Exception 异常
     */
    FollowEntity selectFollow(String userId, Long followeeId) throws Exception;

    /**
     * 新增关注
     * @param userId uid
     * @param followeeId fid
     * @throws SQLException 异常
     */
    void add(Long userId, Long followeeId) throws SQLException;

    /**
     * 取关
     * @param userId uid
     * @param followeeId fid
     * @throws SQLException 异常
     */
    void delete(Long userId, Long followeeId) throws SQLException;

    /**
     * 获取关注博主的id
     * @param id uid
     * @return 关注博主id
     * @throws Exception 异常
     */
    List<Long> selectFollows(Long id) throws Exception;

    /**
     * 获取互关
     * @param id uid
     * @param selectId fid
     * @return 互关
     * @throws Exception 异常
     */
    FollowEntity getEachFollow(Long id, Long selectId) throws Exception;

    /**
     * 获取粉丝id
     * @param followerId uid
     * @return 粉丝id
     * @throws Exception 异常
     */
    List<Long> selectFollowee(Long followerId) throws Exception;
}
