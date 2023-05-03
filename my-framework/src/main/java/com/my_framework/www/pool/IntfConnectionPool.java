package com.my_framework.www.pool;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author 14629
 */
public interface IntfConnectionPool {
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
