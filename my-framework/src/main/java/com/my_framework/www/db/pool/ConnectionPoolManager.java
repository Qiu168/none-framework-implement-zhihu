package com.my_framework.www.db.pool;



import com.my_framework.www.db.pool.config.DataSourceConfig;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author 14629
 */
public class ConnectionPoolManager {

    private static final DataSourceConfig CONFIG = new DataSourceConfig();
    private static DefaultConnectionPool connectionPool;
    private static final Logger logger = Logger.getLogger(ConnectionPoolManager.class.getName());
    static {
        try {
            connectionPool = new DefaultConnectionPool(CONFIG);
        } catch (SQLException | ClassNotFoundException e) {
            logger.log(Level.SEVERE,"mes,e={0}",e.getMessage());
        }
    }

    public static Connection getConnection() throws InterruptedException, SQLException {
        return connectionPool.getPoolEntry().getConn();
    }

    public static void closeConnection(Connection connection)  {
        connectionPool.close(connection);
    }

    public static void closeAll(Connection connection, Statement statement, ResultSet resultSet) {connectionPool.closeAll(connection,statement,resultSet);}

}
