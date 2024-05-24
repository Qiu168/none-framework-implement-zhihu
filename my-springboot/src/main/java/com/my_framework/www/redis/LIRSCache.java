package com.my_framework.www.redis;

import lombok.Getter;
import lombok.Setter;
import redis.clients.jedis.Jedis;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author _qqiu
 */
public class LIRSCache {
    private static volatile LIRSCache instance;
    private static final Object LOCK = new Object();

    @Setter
    @Getter
    private Jedis jedis;
    private final int capacity;
    /**
     * LIRS堆栈
     */
    private final List<String> lirsStack = new ArrayList<>();
    /**
     * key: 缓存键
     * value: 键最近使用时在lirsStack中的索引
     */
    private final Map<String, Integer> recency = new HashMap<>();
    /**
     * key: 缓存键
     * value: 缓存在lirsStack中的索引
     */
    private final Map<String, Integer> lirRecency = new HashMap<>();

    private LIRSCache(int capacity) {
        this.jedis = JedisUtils.getJedis();
        this.capacity = capacity;
    }

    public static LIRSCache getInstance(int capacity) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = new LIRSCache(capacity);
                }
            }
        }
        return instance;
    }

    public String get(String key) {
        String value = jedis.get(key);
        if (value != null) {
            updateRecency(key);
        }
        return value;
    }

    public void put(String key, String value) {
        if (jedis.dbSize() >= capacity) {
            evict();
        }
        jedis.set(key, value);
        lirsStack.add(key);
        recency.put(key, lirsStack.size() - 1);
        lirRecency.put(key, lirsStack.size() - 1);
    }
    public void delete(String key) {
        if (jedis.exists(key)) {
            jedis.del(key);
        }
        if (recency.containsKey(key)) {
            int index = recency.get(key);
            lirsStack.remove(index);
            recency.remove(key);
            lirRecency.remove(key);
        }
    }
    private void updateRecency(String key) {
        int index = recency.get(key);
        lirsStack.remove(index);
        lirsStack.add(key);
        recency.remove(key);
        recency.put(key, lirsStack.size() - 1);
    }
    private void evict() {
        // 执行淘汰策略，将最近最少使用的键值对从Redis和lirsStack中删除
        String candidate = null;
        int minWeight = Integer.MAX_VALUE;
        for (String key : lirsStack) {
            int recencyVal = recency.get(key);
            int lirRecencyVal = lirRecency.get(key);
            int weight = recencyVal + lirRecencyVal;
            if (weight < minWeight) {
                candidate = key;
                minWeight = weight;
            }
        }
        lirsStack.remove(candidate);
        recency.remove(candidate);
        lirRecency.remove(candidate);
        jedis.del(candidate);
    }
}