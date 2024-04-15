package com.huangTaiQi.www.chatroom.endpoint;



import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;


/**
 * @author _qqiu
 */
public class HttpSessionConfigurator extends ServerEndpointConfig.Configurator {
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        Object httpSession = request.getHttpSession();
        if (httpSession != null) {
            sec.getUserProperties().put("httpSession", httpSession);
        }
    }
//    @Override
//    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
//        WebSocketContainer container = new WebSocketContainer(endpointClass);
//        return endpointClass.cast(container);
//    }
}
