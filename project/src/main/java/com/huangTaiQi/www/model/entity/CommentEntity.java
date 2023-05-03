package com.huangTaiQi.www.model.entity;

/**
 * @author 14629
 */
@SuppressWarnings("ALL")
public class CommentEntity {
    private Long id;
    private Integer state;
    private Long answerId;
    private Long userId;
    private String username;
    private String avatar;
    private Long commentTime;
    private String content;

    /**
     * 父评论的id
     */
    private Long pid;

    /**
     * 一级评论的id
     */
    private Long topId;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Long commentTime) {
        this.commentTime = commentTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getTopId() {
        return topId;
    }

    public void setTopId(Long topId) {
        this.topId = topId;
    }
}
