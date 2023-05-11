package com.huangTaiQi.www.model.entity;

import java.sql.Timestamp;

/**
 * @author 14629
 */
@SuppressWarnings("ALL")
public class FollowEntity {
    private Long id;
    private Long userId;
    private Long followeeId;
    private Timestamp createTime;

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

    public Long getFolloweeId() {
        return followeeId;
    }

    public void setFolloweeId(Long followeeId) {
        this.followeeId = followeeId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
