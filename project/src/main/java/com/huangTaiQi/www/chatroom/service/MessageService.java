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
import com.huangTaiQi.www.utils.UserHolder;
import com.my_framework.www.annotation.Autowired;
import com.my_framework.www.annotation.Service;

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
        //TODO:1,保存消息到数据库中，同时得到生成的消息mid
        Message message = new Message();
        message.setUid(chatRoomUserVO.getUid());
        message.setUsername(UserHolder.getUser().getUsername());
        message.setAvatar(UserHolder.getUser().getAvatar());
        message.setContent(content);
        message.setPublishedAt(System.currentTimeMillis());
        //TODO:真的用得到mid吗
        Integer mid = messageDao.insert(message);
        message.setMid(mid);
        //2,从用户中心中获取当前在线用户
        String messageText= JSON.toJSONString(message);
        List<OnlineUser> onlineUserList = userCenter.getOnlineUserList(chatRoomUserVO.getChatRoomId());
        for (OnlineUser onlineUser : onlineUserList) {
            if(onlineUser.getDo().getChatRoomId().equals(chatRoomUserVO.getChatRoomId())){
                //发送数据到同一个群聊的
                onlineUser.send(messageText);
            } else if(onlineUser.getDo().getChatRoomId()==0){
                //该用户在这个群聊的
                ChatRoomUserEntity userByUidAndRoomId = chatRoomUserDao.getUserByUidAndRoomId(onlineUser.getDo().getUid(), String.valueOf(onlineUser.getDo().getChatRoomId()));
                if(userByUidAndRoomId!=null){
                    //如果还没有进入。发个提示
                    onlineUser.send(String.valueOf(onlineUser.getDo().getChatRoomId()));
                }
            }
        }
    }
}
