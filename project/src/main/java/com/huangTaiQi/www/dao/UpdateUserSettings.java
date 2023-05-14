package com.huangTaiQi.www.dao;

import java.sql.SQLException;

/**
 * @author 14629
 */
public interface UpdateUserSettings {
    /**
     * 跟新冗余字段
     * @param id id
     * @param avatar 头像
     * @param username 用户名
     * @throws SQLException 异常
     */
    void updateSettings(Long id, String avatar, String username) throws SQLException;
}
