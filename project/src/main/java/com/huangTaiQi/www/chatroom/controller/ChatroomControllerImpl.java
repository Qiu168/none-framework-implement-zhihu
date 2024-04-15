package com.huangTaiQi.www.chatroom.controller;

import com.alibaba.fastjson.JSON;
import com.huangTaiQi.www.chatroom.model.UserListResult;
import com.huangTaiQi.www.chatroom.service.ChatRoomUserService;
import com.huangTaiQi.www.chatroom.service.ChatroomService;
import com.huangTaiQi.www.controller.BaseController;
import com.huangTaiQi.www.model.vo.IsSuccessVO;
import com.huangTaiQi.www.service.impl.UserServiceImpl;
import com.huangTaiQi.www.utils.UserHolder;
import com.my_framework.www.core.annotation.bean.Autowired;
import com.my_framework.www.core.annotation.stereotype.Controller;
import com.my_framework.www.webmvc.annotation.RequestMapping;
import com.my_framework.www.webmvc.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author _qqiu
 */
@Controller
@RequestMapping("chatroom")
public class ChatroomControllerImpl extends BaseController implements ChatroomController {
    @Autowired
    ChatroomService chatroomService;
    @Autowired
    ChatRoomUserService chatRoomUserService;
    @Autowired
    UserServiceImpl userService;
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
    public void getUserList(@RequestParam("roomId") String roomId, HttpServletResponse resp) throws Exception {
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
    @Override
    @RequestMapping
    public void addUser(@RequestParam("roomId") String roomId, @RequestParam("username") String username,HttpServletResponse response) throws Exception {
        //查询是否互关，并获取id
        Long followId = userService.isFollowEachOther(UserHolder.getUser(), username);
        response.setContentType("text/json;charset=utf-8");
        if(followId==0){
            //获取不到
            response.getWriter().write(JSON.toJSONString(new IsSuccessVO(false,"没有该互关朋友")));
        }else{
            //拉进群聊
            chatRoomUserService.createNewChatUser(followId,roomId);
            response.getWriter().write(JSON.toJSONString(new IsSuccessVO(true,"成功添加")));
        }
    }
}
