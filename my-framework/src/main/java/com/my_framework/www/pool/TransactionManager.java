package com.my_framework.www.pool;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * @author 14629
 */
public class TransactionManager {
    private final Connection connection;

    public TransactionManager(Connection connection) {
        this.connection = connection;
    }

    public void beginTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    public void commit() throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
    }

    public void rollback() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
    }
}



