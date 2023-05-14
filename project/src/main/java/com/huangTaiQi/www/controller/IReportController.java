package com.huangTaiQi.www.controller;


import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * @author 14629
 */
public interface IReportController {

    void reportMessage( String type,String messageId, String content, HttpServletResponse response) throws SQLException;
}
