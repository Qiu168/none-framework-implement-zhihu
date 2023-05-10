package com.my_framework;

import com.my_framework.www.webmvc.rate.RateLimiter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestRate {
    @Before
    public void setUp() throws Exception {
        //在测试方法执行前，添加一个名为testBucket的令牌桶，最大容量为10，每秒钟放入2个令牌到桶里
        RateLimiter.addBucket("testBucket", 10, 2);
    }

    @Test
    public void testAddBucket() {
        //测试令牌桶是否成功添加到RateLimiter中
        Assert.assertTrue(RateLimiter.hasBucket("testBucket"));
    }

    @Test
    public void testAllowAccess() throws InterruptedException {
        //测试令牌桶是否能够成功控制请求频率
        //Thread.sleep(200); //睡眠 200ms，让桶中有足够多的令牌
        for(int i = 0; i < 10; i++) { //尝试发送10个请求，每个请求需要消耗一个令牌
            String user = "user" + i; //模拟多个用户发送请求
            boolean allowAccess = RateLimiter.allowAccess("testBucket", user, 1); //每个请求消耗一个令牌
            Assert.assertTrue(allowAccess); //每个请求都应该被允许访问
        }
        String user = "user11"; //再添加一个用户
        for (int i = 10; i > 0; i--) {
            RateLimiter.allowAccess("testBucket", user, 1);
        }
        boolean allowAccess = RateLimiter.allowAccess("testBucket", user, 1);
        Assert.assertFalse(allowAccess); //这个请求会失败，因为令牌桶已经没令牌了
    }

    @Test
    public void testHasBucket() {
        //测试令牌桶是否成功添加到RateLimiter中
        Assert.assertTrue(RateLimiter.hasBucket("testBucket"));
        Assert.assertFalse(RateLimiter.hasBucket("fakeBucket"));
    }
}
