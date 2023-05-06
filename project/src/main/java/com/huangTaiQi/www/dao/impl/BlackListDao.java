package com.huangTaiQi.www.dao.impl;

import com.huangTaiQi.www.dao.BaseDao;
import com.huangTaiQi.www.dao.IBlackListDao;
import com.huangTaiQi.www.model.entity.BlackListEntity;
import com.huangTaiQi.www.utils.sql.SQLBuilder;
import com.my_framework.www.annotation.Repository;
import com.my_framework.www.pool.DataBaseUtil;

import java.sql.Connection;
import java.util.List;

/**
 * @author 14629
 */
@Repository
public class BlackListDao implements IBlackListDao {
    private final Connection connection = DataBaseUtil.getConnection();
    private final BaseDao baseDao=new BaseDao(connection);

    public List<BlackListEntity> getBlackListByUid(String uid) throws Exception {
        String sql=new SQLBuilder("black_list")
                .select("*")
                .where("uid")
                .buildSelect();
        return baseDao.selectByParams(sql,BlackListEntity.class,uid);
    }

}
