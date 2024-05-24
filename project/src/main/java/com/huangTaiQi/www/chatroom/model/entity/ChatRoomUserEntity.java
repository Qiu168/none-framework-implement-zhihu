package com.huangTaiQi.www.chatroom.model.entity;


import java.util.Objects;

/**
 * @author _qqiu
 */
public class ChatRoomUserEntity {
    private Integer id;
    private Long uid;
    private Boolean state;
    private Integer chatroomId;
    private Integer role;
    private Long logoutAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Integer getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(Integer chatroomId) {
        this.chatroomId = chatroomId;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Long getLogoutAt() {
        return logoutAt;
    }

    public void setLogoutAt(Long logoutAt) {
        this.logoutAt = logoutAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChatRoomUserEntity that = (ChatRoomUserEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(uid, that.uid) && Objects.equals(state, that.state) && Objects.equals(chatroomId, that.chatroomId) && Objects.equals(role, that.role) && Objects.equals(logoutAt, that.logoutAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uid, state, chatroomId, role, logoutAt);
    }
}
