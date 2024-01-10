package com.huangTaiQi.www.chatroom.service;



import com.alibaba.fastjson.JSON;
import com.huangTaiQi.www.chatroom.center.UserCenter;
import com.huangTaiQi.www.chatroom.core.IMessagePublisher;
import com.huangTaiQi.www.chatroom.dao.ChatRoomUserDao;
import com.huangTaiQi.www.chatroom.dao.MessageDao;
import com.huangTaiQi.www.chatroom.model.entity.ChatRoomUserEntity;
import com.huangTaiQi.www.chatroom.model.entity.Message;
import com.huangTaiQi.www.chatroom.model.OnlineUser;
import com.huangTaiQi.www.chatroom.model.vo.ChatRoomUserVO;
import com.my_framework.www.core.annotation.bean.Autowired;
import com.my_framework.www.core.annotation.stereotype.Service;

import java.util.List;

/**
 * @author 14629
 */
@Service
public class MessageService implements IMessagePublisher {
    @Autowired
    MessageDao messageDao;
    @Autowired
    ChatRoomUserDao chatRoomUserDao;
    private final UserCenter userCenter = UserCenter.getInstance();

    @Override
    public void publish(ChatRoomUserVO chatRoomUserVO, String content) throws Exception {
        Message message = new Message();
        message.setUid(chatRoomUserVO.getUid());
        message.setUsername(chatRoomUserVO.getUsername());
        message.setAvatar(chatRoomUserVO.getAvatar());
        message.setContent(content);
        message.setRoomId(chatRoomUserVO.getChatroomId());
        message.setPublishedAt(System.currentTimeMillis());
        messageDao.insertMessage(message);
        //2,从用户中心中获取当前在线用户
        String messageText= JSON.toJSONString(message);
        List<OnlineUser> onlineUserList = userCenter.getOnlineUserList(chatRoomUserVO.getChatroomId());
        for (OnlineUser onlineUser : onlineUserList) {
            if(onlineUser.getDo().getChatroomId().equals(chatRoomUserVO.getChatroomId())){
                //发送数据到同一个群聊的
                onlineUser.send(messageText);
            } else if(onlineUser.getDo().getChatroomId()==0){
                //该用户在这个群聊的
                ChatRoomUserEntity userByUidAndRoomId = chatRoomUserDao.getUserByUidAndRoomId(onlineUser.getDo().getUid(), String.valueOf(onlineUser.getDo().getChatroomId()));
                if(userByUidAndRoomId!=null){
                    //如果还没有进入。发个提示
                    onlineUser.send(String.valueOf(onlineUser.getDo().getChatroomId()));
                }
            }
        }
    }
}
