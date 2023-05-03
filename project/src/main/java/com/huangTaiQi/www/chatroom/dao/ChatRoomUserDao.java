package com.huangTaiQi.www.chatroom.dao;



import com.huangTaiQi.www.chatroom.model.entity.ChatRoomUserEntity;
import com.huangTaiQi.www.chatroom.model.entity.Chatroom;
import com.huangTaiQi.www.chatroom.model.vo.ChatRoomUserVO;
import com.huangTaiQi.www.dao.BaseDao;
import com.huangTaiQi.www.utils.sql.SQLBuilder;
import com.my_framework.www.annotation.Repository;
import com.my_framework.www.pool.DataBaseUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author 14629
 */
@Repository
public class ChatRoomUserDao {

    private final Connection connection = DataBaseUtil.getConnection();
    BaseDao baseDao=new BaseDao(connection);
    public ChatRoomUserEntity selectOneByUsername(String username) {
        //String sql = "SELECT uid, username, username, password, logout_at FROM users WHERE username = ?";
        String sql=new SQLBuilder("chat_users A")
                .select("A.id","A.chatroom_id","A.state","A.uid","A.role","A.logout_at","B.username","B.avatar")
                .join("user B","A.uid = B.id").where("username").buildSelect();

        return null;
    }

    public void insert(ChatRoomUserEntity chatRoomUserEntity) {
        String sql = "INSERT INTO users (username, username, password) VALUES (?, ?, ?)";

    }

    public List<ChatRoomUserVO> selectAllList(String roomId) {
        List<ChatRoomUserVO> chatRoomUserEntityList = new ArrayList<>();
        //String sql = "SELECT uid, username FROM users ORDER BY uid";

            return chatRoomUserEntityList;
    }

    public ChatRoomUserEntity getUserByUidAndRoomId(Long id, String roomId) {
        return null;
    }

    public void createUser(Long id, String roomId, int role) throws SQLException {
        String sql=new SQLBuilder("chat_user")
                .insert("uid")
                .insert("chatroom_id")
                .insert("logout_at")
                .insert("role")
                .buildInsert();
        baseDao.updateCommon(sql,id,roomId,System.currentTimeMillis(),role);
    }

    public List<Chatroom> getChatroomByUid(String uid) throws Exception {
        String sql=new SQLBuilder("chat_users A")
                .select("A.chatroom_id id")
                .select("B.name")
                .join("chatroom B","A.chatroom_id=B.id")
                .buildSelect();
        return baseDao.selectByParams(sql,Chatroom.class);
    }

    public Integer createChatRoom(String roomName) throws Exception {
        String sql=new SQLBuilder("chatroom").insert("name").buildInsert();
        baseDao.updateCommon(sql,roomName);
        //获取主键
        return baseDao.getLastInsertId();
    }

    public void updateLogoutAt(ChatRoomUserVO chatRoomUserEntityDo) throws SQLException {
        String sql=new SQLBuilder("chatroom").update("logout_at").where("id").buildUpdate();
        baseDao.updateCommon(sql,chatRoomUserEntityDo.getLogoutAt(), chatRoomUserEntityDo.getId());
    }
}
