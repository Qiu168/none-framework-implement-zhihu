package com.huangTaiQi.www.chatroom.core;



import com.huangTaiQi.www.chatroom.model.OnlineUser;

import java.sql.SQLException;


/**
 * @author 14629
 */
public interface IOnlineUser {
    /**
     * 上线
     * @param user 用户
     * @throws Exception 异常
     */
    void online(OnlineUser user) throws Exception;

    /**
     * 下线
     * @param user 用户
     * @throws SQLException 异常
     */
    void offline(OnlineUser user) throws SQLException;
}
