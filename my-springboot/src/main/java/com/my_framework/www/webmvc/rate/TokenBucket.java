package com.my_framework.www.webmvc.rate;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author _qqiu
 */
public class TokenBucket {

    /**
     * 最大令牌数
     */
    private final int maxToken;
    /**
     * 每秒生成的令牌
     */
    private final int ratePerSecond;

    /**
     * key:user
     * 每个用户的当前令牌数量
     */
    private final ConcurrentHashMap<String, AtomicInteger> buckets;
    /**
     * key:user
     * 上次使用的时间
     */
    private final ConcurrentHashMap<String, Long> lastRefillTime;

    public TokenBucket(int maxToken, int ratePerSecond) {
        this.maxToken = maxToken;
        this.ratePerSecond = ratePerSecond;
        this.buckets = new ConcurrentHashMap<>();
        this.lastRefillTime = new ConcurrentHashMap<>();
    }

    public boolean getTokens(String user,int numTokens) {
        AtomicInteger tokenCount = refillToken(user);
        if (numTokens <= tokenCount.get()) {
            tokenCount.addAndGet(-numTokens);
            return true;
        } else {
            return false;
        }
    }

    private synchronized AtomicInteger refillToken(String user) {
        long currentTime = System.currentTimeMillis();
        // 如果第一次就算1000秒的间隔，这里防止int爆了
        long timeElapsed = currentTime - lastRefillTime.getOrDefault(user,currentTime-1000000);
        // 计算现在应该生成多少个令牌
        int newTokens = (int) ((timeElapsed / 1000) * ratePerSecond);
        AtomicInteger tokenCount = buckets.getOrDefault(user,new AtomicInteger(maxToken));
        if (newTokens > 0) {
            lastRefillTime.put(user,currentTime) ;
            // 更新令牌数量
            tokenCount.getAndUpdate(currentCount -> Math.min(currentCount + newTokens, maxToken));
            buckets.put(user,tokenCount);
        }
        return tokenCount;
    }
}