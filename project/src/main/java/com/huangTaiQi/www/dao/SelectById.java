package com.huangTaiQi.www.dao;

import com.huangTaiQi.www.model.UserSettings;

/**
 * @author 14629
 */
public interface SelectById {
    /**
     * 根据主键查询
     * @param id 主键
     * @return entity
     * @throws Exception 异常
     */
    UserSettings selectById(String id) throws Exception;
}
