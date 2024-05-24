package com.huangTaiQi.www.chatroom.core;



import com.huangTaiQi.www.chatroom.model.entity.ChatRoomUserEntity;
import com.huangTaiQi.www.chatroom.model.vo.ChatRoomUserVO;


/**
 * @author _qqiu
 */
public interface IMessagePublisher {
    /**
     * 发送信息
     * @param chatRoomUserVO 发送者
     * @param messageText 发送体
     * @throws Exception 异常
     */
    void publish(ChatRoomUserVO chatRoomUserVO, String messageText) throws Exception;
}
