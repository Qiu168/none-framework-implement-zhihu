package com.huangTaiQi.www.chatroom.center;



import com.huangTaiQi.www.chatroom.core.IOnlineUserRegistry;
import com.huangTaiQi.www.chatroom.model.OnlineUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author 14629
 */
public class UserCenter implements IOnlineUserRegistry {
    private UserCenter() {}

    private static final UserCenter instance = new UserCenter();
    public static UserCenter getInstance() {
        return instance;
    }

    /**
     * 创建一个在线用户列表
     */

    private final List<OnlineUser> onlineUserList = new ArrayList<>();
    @Override
    public void online(OnlineUser user) throws IOException {
        if (onlineUserList.contains(user)) {
            //如果该用户已经在线了，不允许多次登录，所以把上次登录的用户踢下线
            int i = onlineUserList.indexOf(user);
            OnlineUser prevUser = onlineUserList.get(i);
            prevUser.kick();
            return;
        }
        //此前处于不在线状态，将该用户添加到在线列表
        onlineUserList.add(user);
        System.out.println("在线用户: " + onlineUserList);
    }

    @Override
    public void offline(OnlineUser user) {
        onlineUserList.remove(user);
    }

    public List<OnlineUser> getOnlineUserList(Integer roomId) {
        List<OnlineUser> roomOnlineUser=new ArrayList<>();
        for (OnlineUser onlineUser : onlineUserList) {
            if (onlineUser.getDo().getChatRoomId().equals(roomId)) {
                roomOnlineUser.add(onlineUser);
            }
        }
        return roomOnlineUser;
    }
}
