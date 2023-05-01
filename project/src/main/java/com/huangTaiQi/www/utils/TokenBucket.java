package com.huangTaiQi.www.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 14629
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
     * 当前令牌的数量
     */
    private final AtomicInteger tokenCount;
    /**
     * 上次使用的时间
     */
    private long lastRefillTime;

    public TokenBucket(int maxToken, int ratePerSecond) {
        this.maxToken = maxToken;
        this.ratePerSecond = ratePerSecond;
        this.tokenCount = new AtomicInteger(maxToken);
        this.lastRefillTime = System.currentTimeMillis();
    }

    public synchronized boolean getTokens(int numTokens) {
        refillToken();
        if (numTokens <= tokenCount.get()) {
            tokenCount.addAndGet(-numTokens);
            return true;
        } else {
            return false;
        }
    }

    private void refillToken() {
        long currentTime = System.currentTimeMillis();
        long timeElapsed = currentTime - lastRefillTime;
        // 计算现在应该生成多少个令牌
        int newTokens = (int) (timeElapsed / 1000 * ratePerSecond);
        if (newTokens > 0) {
            lastRefillTime = currentTime;
            // 更新令牌数量
            tokenCount.getAndUpdate(currentCount -> Math.min(currentCount + newTokens, maxToken));
        }
    }
}