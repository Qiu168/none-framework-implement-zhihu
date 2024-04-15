package com.my_framework.www.security;

/**
 * @author _qqiu
 */
public interface RightGet {
    /**
     * 获取权限
     * @param rightName 权限名
     * @return 权限是否存在
     */
    Boolean getRight(Long rightName);
}
