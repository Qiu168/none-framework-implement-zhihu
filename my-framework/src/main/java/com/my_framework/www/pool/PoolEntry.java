package com.my_framework.www.pool;

import java.sql.Connection;

/**
 * 记录每一个在使用的连接
 * @author 14629
 */
public class PoolEntry {
    private Connection conn;
    private long useStartTime;

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public long getUseStartTime() {
        return useStartTime;
    }

    public void setUseStartTime(long useStartTime) {
        this.useStartTime = useStartTime;
    }

    public PoolEntry(Connection conn, long useStartTime) {
        super();
        this.conn = conn;
        this.useStartTime = useStartTime;
    }
}
