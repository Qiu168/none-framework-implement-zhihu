package com.huangTaiQi.www.service;

import java.sql.SQLException;

/**
 * @author 14629
 */
public interface BlackListService {
    /**
     * 获取黑名单
     * @return 黑名单
     * @throws Exception 异常
     */
    String getBlackListByUid() throws Exception;

    /**
     * 新增拉黑
     * @param blackUid bid
     * @throws SQLException 异常
     */
    void addBlackList(String blackUid) throws SQLException;

    /**
     * 取消拉黑
     * @param blackUid bid
     * @throws SQLException 异常
     */
    void deleteBlackList(String blackUid) throws SQLException;
}
