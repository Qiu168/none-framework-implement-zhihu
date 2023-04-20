package com.my_framework.www.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;


/**
 * TODO:事务
 * @author 14629
 */
public class JedisPoolFactory {
    private static final JedisPool JEDIS_POOL;

    static {
        JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
        //最大连接数
        jedisPoolConfig.setMaxTotal(16);
        //最大空闲连接
        jedisPoolConfig.setMaxIdle(16);
        //最小空闲连接
        jedisPoolConfig.setMinIdle(0);
        //最大等待时间 200ms
        jedisPoolConfig.setMaxWait(Duration.ofMillis(500));
        JEDIS_POOL=new JedisPool(jedisPoolConfig,"192.168.181.132",6379,1000,"redis");
    }
    public static Jedis getResource(){
        return JEDIS_POOL.getResource();
    }
}
