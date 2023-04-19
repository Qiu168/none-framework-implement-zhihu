package com.huangTaiQi.www.model.entity;

/**
 * @author 14629
 */
@SuppressWarnings("ALL")
public class BanEntity {
    private Long id;
    private Long userId;
    private Long rightId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRightId() {
        return rightId;
    }

    public void setRightId(Long rightId) {
        this.rightId = rightId;
    }
}