package com.my_framework.www.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;


/**
 * @author _qqiu
 */
public class JedisPoolFactory {
    private static final JedisPool JEDIS_POOL;

    static {
        JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
        //最大连接数
        jedisPoolConfig.setMaxTotal(32);
        //最大空闲连接
        jedisPoolConfig.setMaxIdle(16);
        //最小空闲连接
        jedisPoolConfig.setMinIdle(0);
        //最大等待时间 200ms
        jedisPoolConfig.setMaxWait(Duration.ofMillis(500));
        JEDIS_POOL=new JedisPool(jedisPoolConfig,"39.108.105.73",37690,1000,"aliyunbaotaredissz");
    }
    public static Jedis getResource(){
        Jedis jedis = JEDIS_POOL.getResource();
        jedis.auth("aliyunbaotaredissz");
        return jedis;
    }
}
