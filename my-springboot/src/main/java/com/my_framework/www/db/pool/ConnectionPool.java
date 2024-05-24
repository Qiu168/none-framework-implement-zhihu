package com.my_framework.www.db.pool;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author _qqiu
 */
public interface ConnectionPool {
    /**
     * 创造一个连接
     * @return 一个连接
     * @throws SQLException 异常
     */
    Connection createConnection() throws SQLException;

    /**
     * 释放连接
     * @param conn 需要释放的连接
     * @throws SQLException 异常
     */
    void close(Connection conn) throws SQLException;
}
