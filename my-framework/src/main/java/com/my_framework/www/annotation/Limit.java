package com.my_framework.www.annotation;

import java.lang.annotation.*;

/**
 * @author 14629
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Limit {
    /**
     * 令牌桶最大容量
     * @return 令牌桶最大容量
     */
    int maxToken();

    /**
     * 每秒生成的令牌数
     * @return 每秒生成的令牌数
     */
    int ratePerSecond();

    /**
     * 每次请求消耗的令牌数
     * @return 每次请求消耗的令牌数
     */
    int costPerRequest();
}
