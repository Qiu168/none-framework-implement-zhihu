package com.huangTaiQi.www.chatroom.dao;




import com.huangTaiQi.www.chatroom.model.entity.Message;
import com.huangTaiQi.www.dao.BaseDao;
import com.huangTaiQi.www.utils.sql.SQLBuilder;
import com.my_framework.www.annotation.Repository;
import com.my_framework.www.pool.DataBaseUtil;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 14629
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

    public Integer insert(Message message) throws Exception {
        String sql=new SQLBuilder("message")
                .insert("uid")
                .insert("content")
                .insert("published_at")
                .insert("room_id")
                .buildInsert();
        baseDao.updateCommon(sql,message.getUid(),message.getContent(),message.getPublishedAt(),message.getRoomId());
        return baseDao.getLastInsertId();

    }
}
