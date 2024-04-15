package com.huangTaiQi.www.chatroom.model;

import com.huangTaiQi.www.chatroom.model.entity.ChatRoomUserEntity;
import com.huangTaiQi.www.chatroom.model.vo.ChatRoomUserVO;

import javax.websocket.CloseReason;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Objects;

import static javax.websocket.CloseReason.CloseCodes.NORMAL_CLOSURE;


/**
 * 存放session对象
 * @author _qqiu
 */

public class OnlineUser {
    private final ChatRoomUserVO chatRoomUserVO;
    private final Session session;

    public OnlineUser(String username, String avatar, ChatRoomUserEntity chatRoomUserEntity, Session session) {
        this.chatRoomUserVO = new ChatRoomUserVO(chatRoomUserEntity);
        this.chatRoomUserVO.setAvatar(avatar);
        this.chatRoomUserVO.setUsername(username);
        this.session = session;
    }

    public void kick() throws IOException {
        CloseReason reason = new CloseReason(NORMAL_CLOSURE, "账号在别处登录");
        session.close(reason);
    }



    public ChatRoomUserVO getDo() {
        return chatRoomUserVO;
    }

    public void send(String messageText) throws IOException {
        session.getBasicRemote().sendText(messageText);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OnlineUser that = (OnlineUser) o;
        return Objects.equals(chatRoomUserVO, that.chatRoomUserVO) && Objects.equals(session, that.session);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatRoomUserVO, session);
    }
}
