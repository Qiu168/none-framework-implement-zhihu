package com.huangTaiQi.www.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

/**
 * @author 14629
 */
public class JedisPoolFactory {
    private static final JedisPool JEDIS_POOL;
    static {
        JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
        //最大连接数
        jedisPoolConfig.setMaxTotal(8);
        //最大空闲连接
        jedisPoolConfig.setMaxIdle(8);
        //最小空闲连接
        jedisPoolConfig.setMinIdle(2);
        //最大等待时间 200ms
        jedisPoolConfig.setMaxWait(Duration.ofMillis(200));
        JEDIS_POOL=new JedisPool(jedisPoolConfig,"192.168.181.128",6379,1000,"redis");
    }

    public static Jedis getJedis(){
        return JEDIS_POOL.getResource();
    }
}
