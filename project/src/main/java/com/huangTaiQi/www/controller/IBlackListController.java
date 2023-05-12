package com.huangTaiQi.www.controller;

import com.my_framework.www.annotation.RequestMapping;
import com.my_framework.www.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author 14629
 */
public interface IBlackListController {
    /**
     * 拉黑用户
     * @param bid 被拉黑的id
     * @param response resp
     * @throws SQLException 异常
     * @throws IOException 异常
     */
    void addBlackList(String bid, HttpServletResponse response) throws SQLException, IOException;

    /**
     * 取消拉黑
     * @param bid id
     * @throws SQLException 异常
     */
    void deleteBlackList(String bid) throws SQLException;
}
