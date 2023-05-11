package com.huangTaiQi.www.dao.impl;

import com.huangTaiQi.www.dao.BaseDao;
import com.huangTaiQi.www.dao.IBlackListDao;
import com.huangTaiQi.www.model.entity.BlackListEntity;
import com.huangTaiQi.www.utils.sql.SQLBuilder;
import com.my_framework.www.annotation.Repository;
import com.my_framework.www.pool.DataBaseUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 14629
 */
@Repository
public class BlackListDao implements IBlackListDao {
    private final Connection connection = DataBaseUtil.getConnection();
    private final BaseDao baseDao=new BaseDao(connection);
    @Override
    public List<BlackListEntity> getBlackListByUid(Long uid) throws Exception {
        String sql=new SQLBuilder("black_list")
                .select("*")
                .where("uid")
                .buildSelect();
        return baseDao.selectByParams(sql,BlackListEntity.class,uid);
    }
    @Override
    public void addBlackList(Long userId, String blackUid) throws SQLException {
        String sql=new SQLBuilder("black_list")
                .insert("uid")
                .insert("bid")
                .buildInsert();
        baseDao.updateCommon(sql,userId,blackUid);
    }
    @Override
    public void deleteBlackList(Long userId, String blackUid) throws SQLException {
        String sql=new SQLBuilder("black_list")
                .where("uid")
                .where("bid")
                .buildDelete();
        baseDao.updateCommon(sql,userId,blackUid);
    }
}
