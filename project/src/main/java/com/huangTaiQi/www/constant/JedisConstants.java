package com.huangTaiQi.www.constant;

/**
 * @author _qqiu
 */
public class JedisConstants {
    public static final String LOGIN_CODE_KEY = "login:code:";
    /**
     * 邮箱登录验证码有效期600s，10分钟
     */
    public static final Long LOGIN_CODE_TTL = 600L;
    /**
     * 发送邮箱的图形验证码
     */
    public static final String SEND_CODE_KEY = "send:code:";
    /**
     * 图形验证码有效期1800s，半小时
     */
    public static final Long SEND_CODE_TTL = 1800L;
    public static final String LOGIN_USER_KEY = "login:token:";
    public static final Long LOGIN_USER_TTL = 1800L;

    public static final String USER_RIGHT_KEY = "user:right:";
    public static final Long USER_RIGHT_TTL = 1800L;
    public static final String BAN_USER_RIGHT="ban:user:right:";
    public static final Long CACHE_NULL_TTL = 2L;

    public static final Long CACHE_SHOP_TTL = 30L;
    public static final String CACHE_SHOP_KEY = "cache:answer:";

    public static final Long LOCK_SHOP_TTL = 10L;

    public static final String BLOG_LIKED_KEY = "blog:liked:";
    public static final String FEED_QUESTION_KEY = "feed:question:";
    public static final String FEED_ANSWER_KEY = "feed:answer:";
    public static final String SHOP_GEO_KEY = "shop:geo:";
    public static final String USER_SIGN_KEY = "sign:";
    public static final int REDIS_CACHE_CAPACITY = 200;
}
