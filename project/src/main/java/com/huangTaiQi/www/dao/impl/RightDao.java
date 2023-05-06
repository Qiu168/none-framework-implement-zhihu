package com.huangTaiQi.www.dao.impl;

import com.huangTaiQi.www.dao.BaseDao;
import com.huangTaiQi.www.dao.IRightDao;
import com.huangTaiQi.www.model.entity.RoleRightRelationEntity;
import com.huangTaiQi.www.utils.sql.SQLBuilder;
import com.my_framework.www.annotation.Repository;
import com.my_framework.www.pool.DataBaseUtil;

import java.sql.Connection;
import java.util.List;

/**
 * @author 14629
 */
@Repository
public class RightDao implements IRightDao {
    private final Connection connection = DataBaseUtil.getConnection();
    private final BaseDao baseDao=new BaseDao(connection);
    public List<RoleRightRelationEntity> getUserRight(Long roleId) throws Exception {
        String sql=new SQLBuilder("role_right_relation")
                .select("*")
                .where("role_id")
                .buildSelect();
        return baseDao.selectByParams(sql, RoleRightRelationEntity.class,roleId);
    }
}
