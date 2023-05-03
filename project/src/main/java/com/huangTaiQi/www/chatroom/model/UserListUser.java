package com.huangTaiQi.www.chatroom.model;


/**
 * @author 14629
 */
public class UserListUser implements Comparable<UserListUser> {
    private Long uid;
    private String username;
    private String avatar;
    private boolean online;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    @Override
    public int compareTo(UserListUser o) {
        // 比较this和o的大小
        if (this.online != o.online) {
            if (this.online) {
                //谁在线谁小
                return -1;
            } else {
                return 1;
            }
        }
        //在线状态相等，以uid进行比较
        return (int) (this.uid - o.uid);
    }
}
