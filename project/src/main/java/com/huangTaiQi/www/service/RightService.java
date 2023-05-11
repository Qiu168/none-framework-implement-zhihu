package com.huangTaiQi.www.service;

/**
 * @author 14629
 */
public interface RightService {
    /**
     * 禁用权限
     * @param right 权限
     * @param banTime 禁用时间
     */
    void banUserRight(Integer right, Long banTime);

    /**
     * 获取权限
     * @return 用户的全部权限
     */
    String getRight();
}
