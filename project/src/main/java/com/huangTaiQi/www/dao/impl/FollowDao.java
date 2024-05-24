package com.huangTaiQi.www.dao.impl;

import com.my_framework.www.db.orm.BaseDao;
import com.huangTaiQi.www.dao.IFollowDao;
import com.huangTaiQi.www.model.entity.FollowEntity;
import com.my_framework.www.db.orm.sql.SQLBuilder;
import com.my_framework.www.core.annotation.stereotype.Repository;
import com.my_framework.www.db.pool.DataBaseUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static com.huangTaiQi.www.constant.EntityAttributeConstants.ALL;

/**
 * @author _qqiu
 */
@Repository
public class FollowDao implements IFollowDao {
    private final Connection connection = DataBaseUtil.getConnection();
    private final BaseDao baseDao=new BaseDao(connection);
    @Override
    public FollowEntity selectFollow(String userId, Long followeeId) throws Exception {
        String sql=new SQLBuilder("follow")
                .select(ALL)
                .where("user_id")
                .where("followee_id")
                .buildSelect();
        return baseDao.selectOne(sql,FollowEntity.class,userId,followeeId);
    }
    @Override
    public void add(Long userId, Long followeeId) throws SQLException {
        String sql=new SQLBuilder("follow")
                .insert("user_id")
                .insert("followee_id")
                .buildInsert();
        baseDao.updateCommon(sql,userId,followeeId);
    }
    @Override
    public void delete(Long userId, Long followeeId) throws SQLException {
        String sql=new SQLBuilder("follow")
                .where("user_id")
                .where("followee_id")
                .buildDelete();
        baseDao.updateCommon(sql,userId,followeeId);
    }

    @Override
    public List<Long> selectFollows(Long id) throws Exception {
        String sql=new SQLBuilder("follow")
                .select(ALL)
                .where("followee_id")
                .buildSelect();
        List<FollowEntity> followEntities = baseDao.selectByParams(sql, FollowEntity.class, id);
        if(followEntities==null){
            return null;
        }
        return followEntities.stream().map(FollowEntity::getUserId).collect(Collectors.toList());
    }
    @Override
    public FollowEntity getEachFollow(Long id, Long selectId) throws Exception {
        String sql=new SQLBuilder("follow A")
                .select("A.id","A.user_id","A.followee_id","A.create_time")
                .join("follow B","A.user_id=B.followee_id","A.followee_id=B.user_id")
                .where("B.user_id")
                .where("A.user_id")
                .buildSelect();
        return baseDao.selectOne(sql,FollowEntity.class,id,selectId);
    }
    @Override
    public List<Long> selectFollowee(Long followerId) throws Exception {
        String sql=new SQLBuilder("follow")
                .select(ALL)
                .where("user_id")
                .buildSelect();
        List<FollowEntity> followEntities = baseDao.selectByParams(sql, FollowEntity.class, followerId);
        if(followEntities==null){
            return null;
        }
        return followEntities.stream().map(FollowEntity::getFolloweeId).collect(Collectors.toList());
    }
}
