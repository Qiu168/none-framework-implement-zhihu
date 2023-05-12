package com.huangTaiQi.www.dao;

import java.sql.SQLException;

/**
 * @author 14629
 */
public interface ReportAble {
    /**
     * 举报消息
     * @param messageId 消息id
     * @param reporterId 举报人id
     */
    void report(String messageId, Long reporterId) throws SQLException;
}
