package com.huangTaiQi.www.model.entity;

/**
 * @author 14629
 */
@SuppressWarnings("ALL")
public class RoleRightRelationEntity {
    private Long id;
    private Long roleId;
    private Long rightId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getRightId() {
        return rightId;
    }

    public void setRightId(Long rightId) {
        this.rightId = rightId;
    }
}
