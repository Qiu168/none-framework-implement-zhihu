package com.huangTaiQi.www.dao.impl;

import com.huangTaiQi.www.dao.BaseDao;
import com.huangTaiQi.www.model.entity.FollowEntity;
import com.huangTaiQi.www.utils.sql.SQLBuilder;
import com.my_framework.www.annotation.Repository;
import com.my_framework.www.pool.DataBaseUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 14629
 */
@Repository
public class FollowDao {
    private final Connection connection = DataBaseUtil.getConnection();
    private final BaseDao baseDao=new BaseDao(connection);

    public FollowEntity selectFollow(String userId, Long followeeId) throws Exception {
        String sql=new SQLBuilder("follow")
                .select("*")
                .where("user_id")
                .where("followee_id")
                .buildSelect();
        return baseDao.selectOne(sql,FollowEntity.class,userId,followeeId);
    }

    public void add(Long userId, Long followeeId) throws SQLException {
        String sql=new SQLBuilder("follow")
                .insert("user_id")
                .insert("followee_id")
                .buildInsert();
        baseDao.updateCommon(sql,userId,followeeId);
    }

    public void delete(Long userId, Long followeeId) throws SQLException {
        String sql=new SQLBuilder("follow")
                .where("user_id")
                .where("followee_id")
                .buildDelete();
        baseDao.updateCommon(sql,userId,followeeId);
    }


    public List<Long> selectFollows(Long id) throws Exception {
        String sql=new SQLBuilder("follow")
                .select("*")
                .where("followee_id")
                .buildSelect();
        List<FollowEntity> followEntities = baseDao.selectByParams(sql, FollowEntity.class, id);
        return followEntities.stream().map(FollowEntity::getUserId).collect(Collectors.toList());
    }
}
