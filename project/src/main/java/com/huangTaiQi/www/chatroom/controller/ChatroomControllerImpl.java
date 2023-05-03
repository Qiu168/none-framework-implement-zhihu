package com.huangTaiQi.www.chatroom.controller;

import com.alibaba.fastjson.JSON;
import com.huangTaiQi.www.chatroom.model.UserListResult;
import com.huangTaiQi.www.chatroom.service.ChatRoomUserService;
import com.huangTaiQi.www.chatroom.service.ChatroomService;
import com.huangTaiQi.www.controller.BaseController;
import com.my_framework.www.annotation.Autowired;
import com.my_framework.www.annotation.Controller;
import com.my_framework.www.annotation.RequestMapping;
import com.my_framework.www.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


/**
 * @author 14629
 */
@Controller
@RequestMapping("chatroom")
public class ChatroomControllerImpl extends BaseController implements ChatroomController {
    @Autowired
    ChatroomService chatroomService;
    @Autowired
    ChatRoomUserService chatRoomUserService;
    @Override
    @RequestMapping
    public void getChatroomByUid(@RequestParam("uid") String uid, HttpServletResponse response) throws Exception {
        String chatroomJson = chatroomService.getChatroomByUid(uid);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(chatroomJson);
    }

    @Override
    @RequestMapping
    public void createChatroom(@RequestParam("name") String roomName, HttpServletResponse response) throws Exception {
        Integer id=chatroomService.createChatRoom(roomName);
        response.getWriter().write(String.valueOf(id));
    }

    @Override
    @RequestMapping
    public void getUserList(@RequestParam("roomId") String roomId, HttpServletResponse resp) throws IOException {
        UserListResult result = chatRoomUserService.getUserList(roomId);
        String json= JSON.toJSONString(result);
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        resp.getWriter().println(json);
    }
    @Override
    @RequestMapping
    public void getCurrentUser(HttpServletRequest req, HttpServletResponse resp) {


        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        //resp.getWriter().println(s);
    }

    @RequestMapping
    public void newNumber(@RequestParam("roomId") String roomId,@RequestParam("id") Long id) throws SQLException {
        chatRoomUserService.createNewChatUser(id,roomId);
    }
}
