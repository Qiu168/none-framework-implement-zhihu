package com.huangTaiQi.www.controller;


import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * @author _qqiu
 */
public interface IReportController {
    /**
     * 举报信息
     * @param type 类型
     * @param messageId id
     * @param content 举报内容
     * @param response resp
     * @throws SQLException 异常
     */
    void reportMessage( String type,String messageId, String content, HttpServletResponse response) throws SQLException;
}
