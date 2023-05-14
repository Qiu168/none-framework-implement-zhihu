package com.huangTaiQi.www.dao.impl;

import com.huangTaiQi.www.dao.BaseDao;
import com.huangTaiQi.www.dao.ILikeDao;
import com.huangTaiQi.www.model.entity.LikeEntity;
import com.huangTaiQi.www.utils.sql.SQLBuilder;
import com.my_framework.www.annotation.Repository;
import com.my_framework.www.pool.DataBaseUtil;

import java.sql.Connection;
import java.sql.SQLException;

import static com.huangTaiQi.www.constant.EntityAttributeConstants.ALL;

/**
 * @author 14629
 */
@Repository
public class LikeDao implements ILikeDao {
    private final Connection connection = DataBaseUtil.getConnection();
    private final BaseDao baseDao=new BaseDao(connection);
    @Override
    public LikeEntity selectLike(Long userId, String answerId) throws Exception {
        String sql=new SQLBuilder("likes")
                .select(ALL)
                .where("user_id")
                .where("answer_id")
                .buildSelect();
        return baseDao.selectOne(sql, LikeEntity.class,userId,answerId);
    }
    @Override
    public void addLike(Long id, String answerId) throws SQLException {
        String sql=new SQLBuilder("likes")
                .insert("user_id")
                .insert("answer_id")
                .buildInsert();
        baseDao.updateCommon(sql,id,answerId);
    }
    @Override
    public void deleteLike(Long id, String answerId) throws SQLException {
        String sql=new SQLBuilder("likes")
                .where("user_id")
                .where("answer_id")
                .buildDelete();
        baseDao.updateCommon(sql,id,answerId);
    }
}
