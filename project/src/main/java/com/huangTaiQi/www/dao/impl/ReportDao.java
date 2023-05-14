package com.huangTaiQi.www.dao.impl;

import com.huangTaiQi.www.dao.BaseDao;
import com.huangTaiQi.www.dao.IReportDao;
import com.huangTaiQi.www.model.entity.ReportEntity;
import com.huangTaiQi.www.utils.sql.SQLBuilder;
import com.my_framework.www.annotation.Repository;
import com.my_framework.www.pool.DataBaseUtil;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author 14629
 */
@Repository
public class ReportDao implements IReportDao {
    private final Connection connection = DataBaseUtil.getConnection();
    private final BaseDao baseDao=new BaseDao(connection);
    public void addReport(Long reporterId, String messageId, String type, String content) throws SQLException {
        String sql=new SQLBuilder("report")
                .insert("user_id")
                .insert("message_id")
                .insert("type")
                .insert("time")
                .insert("content")
                .buildInsert();
        baseDao.updateCommon(sql,reporterId,messageId,type,System.currentTimeMillis(),content);
    }

    public void updateLegal(String intentional, String messageId, String type) throws SQLException {
        String sql=new SQLBuilder("report")
                .update(ReportEntity::getLegal)
                .where("message_id")
                .where("type")
                .buildUpdate();
        baseDao.updateCommon(sql,intentional,messageId,type);
    }
}
