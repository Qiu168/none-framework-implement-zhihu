package com.huangTaiQi.www.model.entity;


import java.sql.Timestamp;

/**
 * @author 14629
 */
@SuppressWarnings("all")
public class UserEntity {
    private Long id;
    private Long roleId;
    private String username;
    private String password;
    private String email;
    private String avatar;
    private String introduce;
    private Long questionCount;
    private Long answerCount;
    private Long commentCount;
    private Long fans;
    private Long followee;
    private Long blacklist;
    private Boolean gender;
    private Timestamp createTime;
    private Timestamp updateTime;

    public Long getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Long questionCount) {
        this.questionCount = questionCount;
    }

    public Long getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(Long answerCount) {
        this.answerCount = answerCount;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Long getFans() {
        return fans;
    }

    public void setFans(Long fans) {
        this.fans = fans;
    }

    public Long getFollowee() {
        return followee;
    }

    public void setFollowee(Long followee) {
        this.followee = followee;
    }

    public Long getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(Long blacklist) {
        this.blacklist = blacklist;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
