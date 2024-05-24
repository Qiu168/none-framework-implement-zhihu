package com.huangTaiQi.www.dao;

import com.huangTaiQi.www.model.entity.RoleRightRelationEntity;

import java.util.List;

/**
 * @author _qqiu
 */
public interface IRightDao {
    /**
     * 获取用户权限
     * @param roleId 角色id
     * @return 权限
     * @throws Exception 异常
     */
    List<RoleRightRelationEntity> getUserRight(Long roleId) throws Exception;
}
