package com.my_framework.www.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author 14629
 */
public class JedisUtils {
    private static final ThreadLocal<Jedis> TL = new ThreadLocal<>();
    private static final ThreadLocal<Transaction> TRAN = new ThreadLocal<>();
    private static final Logger logger= Logger.getLogger(JedisUtils.class.getName());
    public static Jedis getJedis()  {
        //logger.log(Level.INFO,"获取jedis对象");
        Jedis jedis = TL.get();
        if (jedis == null) {
            jedis = JedisPoolFactory.getResource();
            TL.set(jedis);
        }
        return jedis;
    }
    public static void close() throws SQLException {
        Jedis jedis = getJedis();
        if (jedis != null) {
            jedis.close();
            TL.remove();
        }
        //logger.log(Level.INFO,"归还jedis对象");
    }
    public static void beginTransaction(){
        Jedis jedis = getJedis();
        Transaction multi = jedis.multi();
        TRAN.set(multi);
    }
    public static void commitTransaction() {
        TRAN.get().exec();
        TRAN.remove();
    }

    public static void rollbackTransaction() {
        TRAN.get().discard();
        TRAN.remove();
    }
}
