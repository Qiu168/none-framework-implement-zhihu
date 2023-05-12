package com.huangTaiQi.www.dao;

import com.huangTaiQi.www.model.entity.BlackListEntity;

import java.sql.SQLException;
import java.util.List;

/**
 * @author 14629
 */
public interface IBlackListDao {
    /**
     * 获取黑名单
     * @param uid uid
     * @return 黑名单
     * @throws Exception 异常
     */
    List<BlackListEntity> getBlackListByUid(Long uid) throws Exception;

    /**
     * 拉黑
     * @param userId uid
     * @param blackUid bid
     * @throws SQLException 异常
     */
    void addBlackList(Long userId, String blackUid) throws SQLException;

    /**
     * 取消拉黑
     * @param userId uid
     * @param blackUid bid
     * @throws SQLException 异常
     */
    void deleteBlackList(Long userId, String blackUid) throws SQLException;
}
