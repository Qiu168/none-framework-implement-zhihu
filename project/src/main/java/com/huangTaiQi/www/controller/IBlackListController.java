package com.huangTaiQi.www.controller;



import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author _qqiu
 */
public interface IBlackListController {
    /**
     * 拉黑用户
     * @param bid 被拉黑的id
     * @param response resp
     * @throws SQLException 异常
     * @throws IOException 异常
     */
    void addBlackList(String bid, HttpServletResponse response) throws Exception;

    /**
     * 取消拉黑
     * @param bid id
     * @throws SQLException 异常
     */
    void deleteBlackList(String bid,HttpServletResponse response) throws SQLException, IOException;

    /**
     * 是否被拉黑
     * @param bid bid
     * @param response resp
     * @throws Exception 异常
     */
    void isBlack(String bid, HttpServletResponse response) throws Exception;
}
