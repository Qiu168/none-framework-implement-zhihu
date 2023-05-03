package com.huangTaiQi.www.chatroom.model.vo;

import com.huangTaiQi.www.chatroom.model.entity.ChatRoomUserEntity;

import java.util.Objects;

/**
 * @author 14629
 */
@SuppressWarnings("ALL")
public class ChatRoomUserVO {
    private Integer id;
    private Long uid;
    private Boolean state;
    private Integer chatRoomId;
    private Integer role;
    private Long logoutAt;
    private String avatar;
    private String username;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatRoomUserVO that = (ChatRoomUserVO) o;
        return Objects.equals(id, that.id) && Objects.equals(uid, that.uid) && Objects.equals(state, that.state) && Objects.equals(chatRoomId, that.chatRoomId) && Objects.equals(role, that.role) && Objects.equals(logoutAt, that.logoutAt) && Objects.equals(avatar, that.avatar) && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uid, state, chatRoomId, role, logoutAt, avatar, username);
    }

    public ChatRoomUserVO() {
    }

    public ChatRoomUserVO(ChatRoomUserEntity chatRoomUserEntity) {
        id=chatRoomUserEntity.getId();
        uid=chatRoomUserEntity.getUid();
        state=chatRoomUserEntity.getState();
        chatRoomId=chatRoomUserEntity.getChatRoomId();
        role=chatRoomUserEntity.getRole();
        logoutAt=chatRoomUserEntity.getLogoutAt();
    }

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

    public Integer getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(Integer chatRoomId) {
        this.chatRoomId = chatRoomId;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
