package com.my_framework.www;

import com.my_framework.www.redis.JedisUtils;
import org.junit.After;
import org.junit.Test;
import redis.clients.jedis.Jedis;


import java.util.Map;

public class JedisTest {
    private Jedis jedis= JedisUtils.getJedis();

    @Test
    public void test(){
        String set = jedis.set("name", "123456");
        System.out.println(set);
        String name = jedis.get("name");
        System.out.println("name:"+name);

    }
    @Test
    public void testHash(){
        jedis.hset("user:1","name","htq");
        jedis.hset("user:1","age","18");
        Map<String, String> stringStringMap = jedis.hgetAll("user:1");
        System.out.println(stringStringMap);
    }
    @After
    public void tearDown(){
        if(jedis==null){
            jedis.close();
        }
    }
}
