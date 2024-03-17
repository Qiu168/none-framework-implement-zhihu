package com.my_framework.www;

import com.my_framework.www.redis.LIRSCache;
import org.junit.Test;


public class LRUCacheTest {

    // 初始化缓存
    LIRSCache cache = LIRSCache.getInstance(7);

   @Test
   public void test(){
       // 添加元素
       cache.put("A", "1");
       cache.put("B", "2");
       cache.put("C", "3");
       cache.put("D", "4");
       cache.put("E", "5");
       cache.put("F", "6");

       // 测试get方法，期望获取的值为1
       String value = cache.get("A");
       System.out.println(value); // 输出1

       // 测试put方法，期望缓存大小为3
       cache.put("g", "7");
       System.out.println(cache.getJedis().dbSize());


       cache.delete("C");
       System.out.println(cache.getJedis().dbSize());


       cache.getJedis().close();
   }
}