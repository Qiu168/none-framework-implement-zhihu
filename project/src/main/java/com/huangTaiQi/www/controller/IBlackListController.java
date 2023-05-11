package com.huangTaiQi.www.controller;

import com.my_framework.www.annotation.RequestMapping;
import com.my_framework.www.annotation.RequestParam;

import java.sql.SQLException;

/**
 * @author 14629
 */
public interface IBlackListController {
    /**
     * 拉黑用户
     * @param bid 被拉黑的id
     * @throws SQLException 异常
     */
    void addBlackList(String bid) throws SQLException;

    /**
     * 取消拉黑
     * @param bid id
     * @throws SQLException 异常
     */
    void deleteBlackList(String bid) throws SQLException;
}
