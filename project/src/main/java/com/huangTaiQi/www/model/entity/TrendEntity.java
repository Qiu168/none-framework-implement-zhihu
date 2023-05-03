package com.huangTaiQi.www.model.entity;

/**
 * @author 14629
 */
@SuppressWarnings("ALL")
public class TrendEntity {
    private Long id;
    private Long userId;
    private Integer typeId;
    private Long time;

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

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
