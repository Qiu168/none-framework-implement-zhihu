package com.huangTaiQi.www.chatroom.dao;



import com.huangTaiQi.www.chatroom.model.entity.ChatRoomUserEntity;
import com.huangTaiQi.www.chatroom.model.entity.Chatroom;
import com.huangTaiQi.www.chatroom.model.vo.ChatRoomUserVO;
import com.my_framework.www.db.orm.BaseDao;
import com.my_framework.www.db.orm.sql.SQLBuilder;
import com.my_framework.www.core.annotation.stereotype.Repository;
import com.my_framework.www.db.pool.DataBaseUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author _qqiu
 */
@Repository
public class ChatRoomUserDao {

    private final Connection connection = DataBaseUtil.getConnection();
    BaseDao baseDao=new BaseDao(connection);
    public ChatRoomUserEntity selectOneByUsername(String username) {
        String sql=new SQLBuilder("chat_users A")
                .select("A.id","A.chatroom_id","A.state","A.uid","A.role","A.logout_at","B.username","B.avatar")
                .join("user B","A.uid = B.id").where("username").buildSelect();

        return null;
    }

    public void insert(ChatRoomUserEntity chatRoomUserEntity) {
        String sql = "INSERT INTO users (username, username, password) VALUES (?, ?, ?)";

    }

    public List<ChatRoomUserVO> selectAllList(String roomId) throws Exception {
        List<ChatRoomUserVO> chatRoomUserEntityList = new ArrayList<>();
        String sql=new SQLBuilder("chat_users A")
                .join("user B","A.uid=B.id")
                .select("A.id","A.uid","A.state","A.chatroom_id","A.role","A.logout_at","B.avatar","B.username")
                .where("A.chatroom_id")
                .buildSelect();
        return baseDao.selectByParams(sql,ChatRoomUserVO.class,roomId);
    }

    public ChatRoomUserEntity getUserByUidAndRoomId(Long uid, String roomId) throws Exception {
        String sql=new SQLBuilder("chat_users")
                .select("*")
                .where("uid")
                .where("chatroom_id")
                .buildSelect();
        return baseDao.selectOne(sql,ChatRoomUserEntity.class,uid,roomId);
    }

    public void createUser(Long id, String roomId, int role) throws SQLException {
        String sql=new SQLBuilder("chat_users")
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
                .where("uid")
                .buildSelect();
        return baseDao.selectByParams(sql,Chatroom.class,uid);
    }

    public Integer createChatRoom(String roomName) throws Exception {
        String sql=new SQLBuilder("chatroom").insert("name").buildInsert();
        baseDao.updateCommon(sql,roomName);
        //获取主键
        return selectChatRoomByRoomName(roomName).getId();
    }

    private Chatroom selectChatRoomByRoomName(String roomName) throws Exception {
        String sql=new SQLBuilder("chatroom")
                .select("*")
                .where("name")
                .buildSelect();
        return baseDao.selectOne(sql,Chatroom.class,roomName);
    }

    public void updateLogoutAt(ChatRoomUserVO chatRoomUserEntityDo) throws SQLException {
        String sql=new SQLBuilder("chat_users").update(ChatRoomUserEntity::getLogoutAt).where("id").buildUpdate();
        baseDao.updateCommon(sql,chatRoomUserEntityDo.getLogoutAt(), chatRoomUserEntityDo.getId());
    }
}
