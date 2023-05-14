package com.huangTaiQi.www.service;

import com.huangTaiQi.www.model.entity.BlackListEntity;
import com.huangTaiQi.www.model.vo.IsSuccessVO;

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
     *
     * @param blackUid bid
     * @return 信息
     * @throws SQLException 异常
     */
    IsSuccessVO addBlackList(String blackUid) throws Exception;

    /**
     * 取消拉黑
     * @param blackUid bid
     * @throws SQLException 异常
     */
    void deleteBlackList(String blackUid) throws SQLException;

    /**
     * 是否拉黑
     * @param bid bid
     * @return 是否拉黑
     * @throws Exception 异常
     */
    BlackListEntity selectBlackList(String bid) throws Exception;
}
