package com.my_framework;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

//public class TestJedis {
//    private Jedis jedis;
//    private JedisPool jedisPool;
//    @Before
//    public void setup(){
//        jedis=new Jedis("192.168.181.128",6379);
//        jedis.auth("redis");
//        jedis.select(0);
//    }
//    @Test
//    public void test(){
//        String set = jedis.set("name", "胡歌");
//        System.out.println(set);
//        String name = jedis.get("name");
//        System.out.println("name:"+name);
//
//    }
//    @Test
//    public void testHash(){
//        jedis.hset("user:1","name","htq");
//        jedis.hset("user:1","age","18");
//        Map<String, String> stringStringMap = jedis.hgetAll("user:1");
//        System.out.println(stringStringMap);
//    }
//    @After
//    public void tearDown(){
//        if(jedis==null){
//            jedis.close();
//        }
//    }
//}
