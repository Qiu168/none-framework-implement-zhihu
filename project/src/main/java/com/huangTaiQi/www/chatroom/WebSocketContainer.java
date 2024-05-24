package com.huangTaiQi.www.chatroom;

import com.huangTaiQi.www.chatroom.endpoint.MessageEndpoint;

import javax.servlet.ServletException;
import javax.websocket.*;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author _qqiu
 */
public class WebSocketContainer {
    /**
     * 保存所有已连接的WebSocket会话
     */

    private final Map<String, Session> sessions = new ConcurrentHashMap<>();
    /**
     * WebSocket端点
      */

    private final Class<?> endpointClass;

    public WebSocketContainer(Class<?> endpointClass) {
        this.endpointClass = endpointClass;
    }

    public void onOpen(Session session, EndpointConfig endpointConfig) throws InstantiationException {
        // 构造Endpoint实例
        Object endpointInstance = createEndpointInstance(endpointClass, endpointConfig);
        // 注入服务实例
        injectService(endpointInstance);
        // 保存会话
        sessions.put(session.getId(), session);
        // 调用onOpen方法
        callMethodOnInstance(endpointInstance, "onOpen", session);
        // 注册消息处理器
        MessageHandler.Whole<String> messageHandler = new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                callMethodOnInstance(endpointInstance, "onMessage", message, session);
            }
        };
        session.addMessageHandler(messageHandler);
    }

    public void onClose(Session session, CloseReason closeReason) throws InstantiationException {
        // 删除会话
        sessions.remove(session.getId());
        // 调用onClose方法
        Object endpointInstance = getEndpointInstance(session.getId());
        callMethodOnInstance(endpointInstance, "onClose", session, closeReason);
    }

    public void onError(Session session, Throwable throwable) throws InstantiationException {
        // 调用onError方法
        Object endpointInstance = getEndpointInstance(session.getId());
        callMethodOnInstance(endpointInstance, "onError", session, throwable);
    }

    public void onMessage(Session session, String message) {
        // do nothing
    }

    private Object createEndpointInstance(Class<?> endpointClass, EndpointConfig endpointConfig) throws InstantiationException {
        ServerEndpointConfig endpointConfigImpl = (ServerEndpointConfig) endpointConfig;
        return endpointConfigImpl.getConfigurator().getEndpointInstance(endpointClass);
    }

    private void injectService(Object endpointInstance) {
        // 注入服务实例

    }

    private Object getEndpointInstance(String sessionId) throws InstantiationException {
        Session session = sessions.get(sessionId);
        ServerEndpointConfig endpointConfig = (ServerEndpointConfig) session.getUserProperties().get(ServerEndpointConfig.class.getName());
        return endpointConfig.getConfigurator().getEndpointInstance(endpointClass);
    }

    private void callMethodOnInstance(Object endpointInstance, String methodName, Object... args) {
        Method[] methods = endpointInstance.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                try {
                    method.invoke(endpointInstance, args);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    throw new RuntimeException("Failed to call method " + methodName + " on Endpoint instance", e);
                }
            }
        }
    }


}