package com.huangTaiQi.www.dao;

import java.sql.SQLException;

/**
 * @author 14629
 */
public interface UpdateUserSettings {
    void updateSettings(Long id, String avatar, String username) throws SQLException;
}
