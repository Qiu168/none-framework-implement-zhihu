package com.my_framework.www.pool;



import java.sql.Connection;
import java.sql.SQLException;

/**
 * 管理connection和事务
 * @author 14629
 */

public class DataBaseUtil {

    private static final ThreadLocal<Connection> TL = new ThreadLocal<>();
    private static final TransactionManager TRANSACTION_MANAGER = new TransactionManager(getConnection());

    public static Connection getConnection()  {
        Connection conn = TL.get();
        if (conn == null) {
            try {
                conn = ConnectionPoolManager.getConnection();
            } catch (InterruptedException | SQLException e) {
                throw new RuntimeException(e);
            }
            TL.set(conn);
        }
        return conn;
    }


    public static void beginTransaction() throws SQLException {
        TRANSACTION_MANAGER.beginTransaction();
    }

    public static void commitTransaction() throws SQLException {
        TRANSACTION_MANAGER.commit();
    }

    public static void rollbackTransaction() throws SQLException {
        TRANSACTION_MANAGER.rollback();
    }

    public static void close() throws SQLException {
        Connection conn = TL.get();
        if (conn != null) {
            ConnectionPoolManager.closeConnection(conn);
            TL.remove();
        }
    }


}
