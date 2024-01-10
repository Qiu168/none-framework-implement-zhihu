package com.huangTaiQi.www.chatroom.controller;


import com.my_framework.www.webmvc.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author 14629
 */
public interface ChatroomController {
    /**
     * 获取用户加入的群聊
     * @param uid 用户id
     * @param response resp
     * @throws Exception 异常
     */
    void getChatroomByUid(@RequestParam("uid") String uid, HttpServletResponse response) throws Exception;

    /**
     * 新建群聊
     * @param roomName 群聊名
     * @param response resp
     * @throws Exception 异常
     */
    void createChatroom(@RequestParam("name") String roomName, HttpServletResponse response) throws Exception;

    /**
     * 获取群聊中的用户
     * @param roomId 群聊id
     * @param resp resp
     * @throws Exception 异常
     */
    void getUserList(@RequestParam("roomId") String roomId, HttpServletResponse resp) throws Exception;

    /**
     * 获取当前的用户
     * @param req req
     * @param resp resp
     */
    void getCurrentUser(HttpServletRequest req, HttpServletResponse resp);

    /**
     * 新增群友
     * @param roomId 群id
     * @param username 新增群友的用户名
     * @param response resp
     * @throws Exception 异常
     */
    void addUser( String roomId, String username, HttpServletResponse response) throws Exception;
}
