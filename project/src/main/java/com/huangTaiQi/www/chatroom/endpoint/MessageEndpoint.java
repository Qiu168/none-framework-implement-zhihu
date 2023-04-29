package com.huangTaiQi.www.chatroom.endpoint;



import com.huangTaiQi.www.chatroom.model.entity.ChatRoomUserEntity;
import com.huangTaiQi.www.chatroom.model.OnlineUser;
import com.huangTaiQi.www.chatroom.service.MessageService;
import com.huangTaiQi.www.chatroom.service.ChatRoomUserService;
import com.huangTaiQi.www.model.dto.UserDTO;
import com.huangTaiQi.www.utils.BeanUtil;
import com.huangTaiQi.www.utils.StringUtils;
import com.my_framework.www.annotation.Autowired;
import com.my_framework.www.redis.JedisUtils;
import redis.clients.jedis.Jedis;


import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.sql.SQLException;
import java.util.Map;

import static com.huangTaiQi.www.constant.JedisConstants.LOGIN_USER_KEY;
import static javax.websocket.CloseReason.CloseCodes.NORMAL_CLOSURE;

/**
 * websocket在建立连接时，会构造HttpSessionConfigurator对象，并且调用其modifyHandshake方法
 * 先执行modifyHandshake，后执行onOpen
 * 这里是固定用法
 * @author 14629
 */

@ServerEndpoint(value = "/chat", configurator = HttpSessionConfigurator.class)
public class MessageEndpoint {
    @Autowired
    private ChatRoomUserService chatRoomUserService;
    @Autowired
    private MessageService messageService;
    private OnlineUser currentUser;


    /**
     * 由于onClose的调用肯定是在onOpen之后的，所以可以在onOpen里保存当前用户
     */

    @OnOpen
    public void onOpen( @PathParam("token") String token,@PathParam("roomId") String roomId, Session session) throws Exception {
        //1，判断当前上线的是哪个用户————从redis中获取当前登录用户信息
        if(StringUtils.isEmpty(token)){
            CloseReason reason = new CloseReason(NORMAL_CLOSURE, "必须先登录");
            session.close(reason);
            return;
        }
        Jedis jedis= JedisUtils.getJedis();
        token=StringUtils.getToken(token);
        String key = LOGIN_USER_KEY + token;
        Map<String, String> userMap = jedis.hgetAll(key);
        if(userMap.isEmpty()){
            CloseReason reason = new CloseReason(NORMAL_CLOSURE, "必须先登录");
            session.close(reason);
            return;
        }
        // 将查询到的hash数据转为UserDTO
        UserDTO user = new UserDTO();
        BeanUtil.fillBeanWithMap(user,userMap);
        ChatRoomUserEntity chatRoomUserEntity =chatRoomUserService.getUserByUidAndRoomId(user.getId(),roomId);
        if (chatRoomUserEntity == null) {
            CloseReason reason = new CloseReason(NORMAL_CLOSURE, "你不是该群的成员");
            session.close(reason);
            return;
            //创建新的
            //chatRoomUserEntity =chatRoomUserService.createNewChatUser(user.getId(),roomId);
        }
        // 记录该用户到在线用户列表中（如果重复上线，把之前的踢下线）
        currentUser = new OnlineUser(user.getUsername(),user.getAvatar(),chatRoomUserEntity, session);
        chatRoomUserService.online(currentUser);
    }

    @OnClose
    public void onClose() throws SQLException {
        chatRoomUserService.offline(currentUser);
    }

    @OnMessage
    public void onMessage(String content) throws Exception {
        messageService.publish(currentUser.getDo(), content);
    }

    @OnError
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

}
