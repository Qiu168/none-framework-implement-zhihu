package com.huangTaiQi.www.service;

/**
 * @author _qqiu
 */
public interface RightService {
    /**
     * 禁用权限
     *
     * @param username
     * @param right    权限
     * @param banTime  禁用时间
     */
    void banUserRight(String username, Integer right, Long banTime) throws Exception;

    /**
     * 获取权限
     * @return 用户的全部权限
     */
    String getRight();

    String getRightByUsername(String username) throws Exception;

    void banUserByUsername(String username) throws Exception;
}
