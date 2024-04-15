package com.my_framework.www.db.pool;





import com.my_framework.www.db.pool.config.DataSourceConfig;

import java.sql.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author _qqiu
 */
public class DefaultConnectionPool implements ConnectionPool {
    private static final Logger logger = Logger.getLogger(DefaultConnectionPool.class.getName());
    private final DataSourceConfig config;
    private final AtomicInteger currentActive =new AtomicInteger(0);
    private final Vector<Connection> freePools=new Vector<>();
    private final Vector<PoolEntry> usePools = new Vector<> ();
    public DefaultConnectionPool(DataSourceConfig config) throws SQLException, ClassNotFoundException {
        this.config=config;
        init();
    }

    private void init() throws SQLException, ClassNotFoundException {
        Class.forName(config.getDriver());
        for (int i = 0; i < Integer.parseInt(config.getInitSize()); i++) {
            Connection conn = createConnection();
            freePools.add(conn);
        }
        check();
    }


    @Override
    public synchronized Connection createConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(config.getUrl(), config.getUserName(), config.getPassWord());
        currentActive.incrementAndGet();//++
        //logger.info("new一个新的连接："+conn);
        return conn;
    }
    private void createConnections() throws SQLException {
        for (int i = 0; i < Integer.parseInt(config.getNumConnectionsToCreate()); i++) {
            Connection conn = createConnection();
            freePools.add(conn);
            currentActive.incrementAndGet();
        }
    }
    public synchronized PoolEntry getPoolEntry() throws InterruptedException, SQLException {
        Connection conn;
        if (!freePools.isEmpty()) {
            conn = freePools.get(0);
            freePools.remove(0);
        } else {
            if (currentActive.get() < Integer.parseInt(config.getMaxSize())) {
                createConnections();
                conn = freePools.get(0);
                freePools.remove(0);
            } else {
                //logger.info(Thread.currentThread().getName() + ",连接池最大连接数为:" + config.getMaxSize() + "已经满了，需要等待...");
                wait(1000);
                return getPoolEntry();
            }
        }
        PoolEntry poolEntry = new PoolEntry(conn, System.currentTimeMillis());
        usePools.add(poolEntry);
//        logger.info(Thread.currentThread().getName() + ",获取并使用连接:" + conn
//                + ",空闲线程数：" + freePools.size() + "," +
//                "再使用线程数:" + usePools.size() + ",总的线程数:" + currentActive.get());
        return poolEntry;
    }


    @Override
    public synchronized void close(Connection conn){
        for ( int i = 0; i < usePools.size();i++ ){
            if(usePools.get(i).getConn()==conn){
                usePools.remove(i);
                if(currentActive.get()>Integer.parseInt(config.getInitSize())){
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        logger.log(Level.SEVERE,"connection:"+conn+"close失败");
                    }
                    currentActive.decrementAndGet();
                    //logger.info("动态缩小了线程池！释放了连接:"+conn);
                }
                break;
            }
        }
        try {
            if (!conn.isClosed()){
                freePools.add(conn);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE,"e="+e.getMessage());
        }
        //logger.info("回收了一个连接:"+conn+",空闲连接数为:"+freePools.size()+",在用线程数为:"+usePools.size());
        notifyAll();
    }
    public synchronized void closeAll(Connection conn, Statement stmt, ResultSet rs) {
        if(rs!=null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(stmt!=null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        close(conn);
    }

    /**
     * 将timer线程设置为守护线程
     * 随着服务器关闭一起关闭
     * 检查线程是否超时
     */
    private void check(){
        //当 health 为 true 时，会开启健康检查功能
        if (Boolean.parseBoolean(config.getHealth())){
            Worker worker = new Worker();
            Timer timer = new Timer(true);
            timer.schedule(worker, Long.parseLong(config.getDelay()), Long.parseLong(config.getPeriod()));
        }
    }

    class Worker extends TimerTask {
        @Override
        public  void run() {
            //logger.info("例行检查...");
            for ( int i = 0; i < usePools.size();i++ ){
                PoolEntry entry = usePools.get(i);
                long startTime = entry.getUseStartTime();
                long currentTime = System.currentTimeMillis();
                if ((currentTime-startTime)> Long.parseLong(config.getTimeout())){
                    Connection conn = entry.getConn();
                    try {
                        if (conn != null && ! conn.isClosed()){
                            conn.close();
                            usePools.remove(i);
                            currentActive.decrementAndGet();//--
                            //logger.info("发现有超时连接强行关闭,"+conn+",空闲线程数："+freePools.size()+","+
                            //        "再使用线程数:"+usePools.size()+",总的线程数:"+currentActive.get());
                        }
                    } catch (SQLException e) {
                        logger.log(Level.SEVERE,"mes,e={0}",e.getMessage());
                    }
                }
            }
        }
    }
}
