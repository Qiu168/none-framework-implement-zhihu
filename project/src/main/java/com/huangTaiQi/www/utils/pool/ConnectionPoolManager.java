package com.huangTaiQi.www.utils.pool;



import com.huangTaiQi.www.utils.pool.config.DataSourceConfig;

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

    private static DataSourceConfig config = new DataSourceConfig();
    private static ImplConnectionPool connectionPool;
    private static Logger logger = Logger.getLogger("com.HuangTaiQi.www.pool.ConnectionPoolManager");
    static {
        try {
            connectionPool = new ImplConnectionPool(config);
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
