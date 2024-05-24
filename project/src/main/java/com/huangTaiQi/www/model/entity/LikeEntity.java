package com.huangTaiQi.www.model.entity;

/**
 * @author _qqiu
 */
@SuppressWarnings("ALL")
public class LikeEntity {
    private Long id;
    private Long userId;
    private Long answerId;

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

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }
}
