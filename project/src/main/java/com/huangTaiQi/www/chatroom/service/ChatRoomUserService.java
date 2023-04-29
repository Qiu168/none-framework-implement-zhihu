package com.huangTaiQi.www.chatroom.service;



import com.alibaba.fastjson.JSON;
import com.huangTaiQi.www.chatroom.center.UserCenter;
import com.huangTaiQi.www.chatroom.core.IOnlineUserRegistry;
import com.huangTaiQi.www.chatroom.dao.ChatRoomUserDao;
import com.huangTaiQi.www.chatroom.dao.MessageDao;
import com.huangTaiQi.www.chatroom.model.*;
import com.huangTaiQi.www.chatroom.model.entity.ChatRoomUserEntity;
import com.huangTaiQi.www.chatroom.model.entity.Message;
import com.huangTaiQi.www.chatroom.model.vo.ChatRoomUserVO;
import com.my_framework.www.annotation.Autowired;
import com.my_framework.www.annotation.Service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.huangTaiQi.www.chatroom.constants.ChatroomConstants.GROUP_USER;


/**
 * @author 14629
 */
@Service
public class ChatRoomUserService implements IOnlineUserRegistry {
    private final UserCenter userCenter = UserCenter.getInstance();
    @Autowired
    ChatRoomUserDao chatRoomUserDao;
    @Autowired
    MessageDao messageDao ;


    @Override
    public void online(OnlineUser user) throws Exception {
        if(user.getDo().getChatRoomId()==0){
            //TODO：查询历史信息

            //查询所在的所有群。
            //获取登出时间。
            //查询红点数
            //
        }else {
            //查询历史消息并发送
            ChatRoomUserVO chatRoomUserEntityDo = user.getDo();
            List<Message> messageList = messageDao.selectListAfter(chatRoomUserEntityDo.getLogoutAt());
            //把得到的每条消息都依次发送给刚登陆的用户
            for (Message message : messageList) {
                String messageText= JSON.toJSONString(message);
                user.send(messageText);
            }
        }
        // 登记用户上线
        userCenter.online(user);
    }

    @Override
    public void offline(OnlineUser user) throws SQLException {
        if(user.getDo().getChatRoomId()!=0){
            //1,更新当前用户的登出时间------从OnlineUser这个业务对象中获取到数据库对应的对象（DO)
            ChatRoomUserVO chatRoomUserEntityDo = user.getDo();
            chatRoomUserEntityDo.setLogoutAt(System.currentTimeMillis());
            chatRoomUserDao.updateLogoutAt(chatRoomUserEntityDo);
        }
        userCenter.offline(user);
    }



    public UserListResult getUserList(String roomId) {
        UserListResult result = new UserListResult();
        //从数据库中读取所有用户
        List<ChatRoomUserVO> allChatRoomUserEntityList = chatRoomUserDao.selectAllList(roomId);
        //从UserCenter中读取在线用户
        List<OnlineUser> onlineUserList = userCenter.getOnlineUserList(Integer.parseInt(roomId));
        Set<ChatRoomUserVO> onlineChatRoomUserSet = new HashSet<>();
        for (OnlineUser onlineUser : onlineUserList) {
            onlineChatRoomUserSet.add(onlineUser.getDo());
        }
        result.setOnlineCount(onlineChatRoomUserSet.size());
        result.setTotalCount(allChatRoomUserEntityList.size());
        for (ChatRoomUserVO chatRoomUserVO : allChatRoomUserEntityList) {
            UserListUser ulu = new UserListUser();
            ulu.setUid(chatRoomUserVO.getUid());
            ulu.setUsername(chatRoomUserVO.getUsername());
            ulu.setAvatar(chatRoomUserVO.getAvatar());
            ulu.setOnline(onlineChatRoomUserSet.contains(chatRoomUserVO));
            result.getUserList().add(ulu);
        }

        //排序
        Collections.sort(result.getUserList());
        return result;

    }

    public ChatRoomUserEntity getUserByUidAndRoomId(Long id, String roomId) {
        return chatRoomUserDao.getUserByUidAndRoomId(id,roomId);
    }

    public void createNewChatUser(Long id, String roomId) throws SQLException {
        chatRoomUserDao.createUser(id,roomId, GROUP_USER);
        //return chatRoomUserDao.getUserByUidAndRoomId(id,roomId);
    }
}
