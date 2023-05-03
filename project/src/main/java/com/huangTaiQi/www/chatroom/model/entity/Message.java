package com.huangTaiQi.www.chatroom.model.entity;

/**
 * @author 14629
 */
public class Message {
    private Integer mid;
    private Integer roomId;
    private Long uid;
    private String avatar;
    private String username;
    private String content;
    private Long publishedAt;

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Long publishedAt) {
        this.publishedAt = publishedAt;
    }
}
