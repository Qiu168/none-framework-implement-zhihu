package com.huangTaiQi.www.service;

import java.sql.SQLException;

/**
 * @author 14629
 */
public interface ReportService {
    /**
     * 举报信息
     * @param type 类型
     * @param messageId id
     * @param content 内容
     * @throws SQLException 异常
     */
    void reportMessage(String type, String messageId, String content) throws SQLException;
}
