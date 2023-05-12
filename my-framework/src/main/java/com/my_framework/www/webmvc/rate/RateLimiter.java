package com.my_framework.www.webmvc.rate;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 14629
 */
public class RateLimiter {
    private static final ConcurrentHashMap<String, TokenBucket> buckets=new ConcurrentHashMap<>();
    public static void addBucket(String name,int maxToken,int rate) {
        buckets.put(name, new TokenBucket(maxToken,rate));
    }

    public static boolean allowAccess(String apiName,String user, int numTokens) {
        TokenBucket bucket = buckets.get(apiName);
        if(bucket == null){
            return false;
        }
        return bucket.getTokens(user,numTokens);
    }

    public static boolean hasBucket(String name){
        return buckets.containsKey(name);
    }
}
