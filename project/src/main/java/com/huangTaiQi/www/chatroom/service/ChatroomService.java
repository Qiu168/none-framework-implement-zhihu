package com.huangTaiQi.www.chatroom.service;

import com.alibaba.fastjson.JSON;
import com.huangTaiQi.www.chatroom.dao.ChatRoomUserDao;
import com.huangTaiQi.www.utils.UserHolder;
import com.my_framework.www.core.annotation.bean.Autowired;
import com.my_framework.www.core.annotation.stereotype.Service;

import static com.huangTaiQi.www.chatroom.constants.ChatroomConstants.GROUP_MASTER;

/**
 * @author _qqiu
 */
@Service
public class ChatroomService {
    @Autowired
    ChatRoomUserDao chatRoomUserDao;
    public String getChatroomByUid(String uid) throws Exception {
        return JSON.toJSONString(chatRoomUserDao.getChatroomByUid(uid));
    }

    public Integer createChatRoom(String roomName) throws Exception {
        //创建群聊
        Integer chatRoomId = chatRoomUserDao.createChatRoom(roomName);
        //创建群主
        chatRoomUserDao.createUser(UserHolder.getUser().getId(), String.valueOf(chatRoomId),GROUP_MASTER);
        return chatRoomId;
    }
}
