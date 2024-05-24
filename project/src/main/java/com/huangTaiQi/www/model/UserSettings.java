package com.huangTaiQi.www.model;

/**
 * 有user冗余字段的类要实现这个接口
 * @author _qqiu
 */
public interface UserSettings {
    /**
     * 获取用户Id
     * @return 用户id
     */
    Long getUserId();

    /**
     * 获取用户名
     * @return 用户名
     */
    String getUsername();

    /**
     * 头像
     * @return 头像
     */
    String getAvatar();
}
