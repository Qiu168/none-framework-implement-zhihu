package com.huangTaiQi.www.chatroom.dao;




import com.huangTaiQi.www.chatroom.model.entity.Message;
import com.my_framework.www.db.orm.BaseDao;
import com.my_framework.www.db.orm.sql.SQLBuilder;
import com.my_framework.www.core.annotation.stereotype.Repository;
import com.my_framework.www.db.pool.DataBaseUtil;

import java.sql.Connection;
import java.util.List;

/**
 * @author _qqiu
 */
@Repository
public class MessageDao {
    private final Connection connection = DataBaseUtil.getConnection();
    BaseDao baseDao=new BaseDao(connection);
    /**
     * 返回消息列表
     */
    public List<Message> selectListAfter(Long logoutAt) throws Exception {

        String sql=new SQLBuilder("message m")
                .select("m.mid","m.uid","u.username","m.content","m.published_at","u.avatar")
                .join("user u","u.id = m.uid")
                .where("published_at >")
                .orderBy("published_at")
                .buildSelect();
        return baseDao.selectByParams(sql, Message.class,logoutAt);
    }

    public void insertMessage(Message message) throws Exception {
        String sql=new SQLBuilder("message")
                .insert("uid")
                .insert("content")
                .insert("published_at")
                .insert("room_id")
                .buildInsert();
        baseDao.updateCommon(sql,message.getUid(),message.getContent(),message.getPublishedAt(),message.getRoomId());

    }
}
