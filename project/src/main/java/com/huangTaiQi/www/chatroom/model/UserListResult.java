package com.huangTaiQi.www.chatroom.model;

import java.util.ArrayList;
import java.util.List;


/**
 * @author 14629
 */
public class UserListResult {
    private Integer onlineCount;
    private Integer totalCount;
    private List<UserListUser> userList = new ArrayList<>();

    public Integer getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(Integer onlineCount) {
        this.onlineCount = onlineCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<UserListUser> getUserList() {
        return userList;
    }

    public void setUserList(List<UserListUser> userList) {
        this.userList = userList;
    }
}
