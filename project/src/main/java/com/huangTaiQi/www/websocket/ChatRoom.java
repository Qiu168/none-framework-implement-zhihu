package com.huangTaiQi.www.websocket;

import java.io.IOException;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author 14629
 */
@ServerEndpoint("/chat")
public class ChatRoom {

    private static final CopyOnWriteArrayList<Session> sessions = new CopyOnWriteArrayList<>();

    @OnOpen
    public void onOpen(Session session) throws IOException {
        // 当客户端加入聊天室时，将其session对象存储在sessions列表中。
        sessions.add(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        // 当客户端发送消息时，我们需要将消息发送给所有其他客户端
        for (Session s : sessions) {
            if (s.isOpen() && !session.getId().equals(s.getId())) {
                s.getBasicRemote().sendText(message);
            }
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        // 当客户端从聊天室中断开连接时，将其session从sessions列表中删除
        sessions.remove(session);
    }
}