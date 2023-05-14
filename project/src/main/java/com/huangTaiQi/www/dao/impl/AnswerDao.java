package com.huangTaiQi.www.dao.impl;

import com.huangTaiQi.www.dao.*;
import com.huangTaiQi.www.model.UserSettings;
import com.huangTaiQi.www.model.entity.AnswerEntity;
import com.huangTaiQi.www.model.vo.QuestionAnswer;
import com.huangTaiQi.www.utils.sql.SQLBuilder;
import com.my_framework.www.annotation.Repository;
import com.my_framework.www.pool.DataBaseUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.huangTaiQi.www.constant.StateConstants.MESSAGE_CHECKED;
import static com.huangTaiQi.www.constant.StateConstants.MESSAGE_REPORTED;

/**
 * @author 14629
 */
@Repository
public class AnswerDao implements IAnswerDao , ReportAble, UpdateUserSettings, SelectById {
    private final Connection connection = DataBaseUtil.getConnection();
    private final BaseDao baseDao=new BaseDao(connection);
    @Override
    public List<QuestionAnswer> getUserAnswer(String userId) throws Exception {
        String sql=new SQLBuilder("answer A")
                .select("A.id","A.question_id","A.state","A.title","A.content","A.likes","B.title questionTitle")
                .join("question B","A.question_id=B.id")
                .where("A.user_id")
                .buildSelect();
        return baseDao.selectByParams(sql, QuestionAnswer.class, userId);
    }
    @Override
    public List<AnswerEntity> getAnswerByQuestionId(String questionId) throws Exception {
        String sql=new SQLBuilder("answer")
                .select("*")
                .where("question_id")
                .where("state")
                .buildSelect();
        return baseDao.selectByParams(sql,AnswerEntity.class,questionId,MESSAGE_CHECKED);
    }
    @Override
    public AnswerEntity getAnswerById(String id) throws Exception {
        String sql=new SQLBuilder("answer")
                .select("*")
                .where("id")
                .buildSelect();
        return baseDao.selectOne(sql,AnswerEntity.class,id);
    }
    @Override
    public void updateLikes(String answerId, int i) throws SQLException {
//        String sql=new SQLBuilder("answer")
//                .update("likes")
//                .where("id")
//                .buildInsert();
        String sql="UPDATE answer SET likes = likes + "+i+" WHERE id = ?";
        baseDao.updateCommon(sql,answerId);
    }
    @Override
    public void addAnswer(Long id, String avatar, String username, String title, String content, String questionId) throws SQLException {
        String sql=new SQLBuilder("answer")
                .insert("question_id")
                .insert("user_id")
                .insert("username")
                .insert("avatar")
                .insert("title")
                .insert("content")
                .buildInsert();
        baseDao.updateCommon(sql,questionId,id,username,avatar,title,content);
    }
    @Override
    public void updateAnswerState(int state, long id) throws SQLException {
        String sql=new SQLBuilder("answer")
                .update(AnswerEntity::getState)
                .where("id")
                .buildUpdate();
        baseDao.updateCommon(sql,state,id);
    }
    @Override
    public List<AnswerEntity> getAnswerByIds(List<Long> ids) throws Exception {
        String sql=new SQLBuilder("answer")
                .select("*")
                .whereIn("id",ids.size())
                .buildSelect();
        return baseDao.selectByParams(sql,AnswerEntity.class,ids.toArray());
    }
    @Override
    public List<AnswerEntity> getAnswerByState(int page, int size, int state) throws Exception {
        String sql=new SQLBuilder("answer")
                .select("*")
                .limit(size)
                .offset((page-1)*size)
                .where("state")
                .buildSelect();
        return baseDao.selectByParams(sql, AnswerEntity.class,state);
    }
    @Override
    public int getAnswerCountByState(int state) throws Exception {
        String sql=new SQLBuilder("answer")
                .count("*")
                .where("state")
                .buildSelect();
        return baseDao.selectOne(sql,Integer.class,state);
    }

    @Override
    public void report(String messageId, Long reporterId) throws SQLException {
        String sql=new SQLBuilder("answer")
                .update(AnswerEntity::getState)
                .where("id")
                .buildUpdate();
        baseDao.updateCommon(sql,MESSAGE_REPORTED,messageId);
    }

    @Override
    public void updateSettings(Long id, String avatar, String username) throws SQLException {
        String sql=new SQLBuilder("answer")
                .update(AnswerEntity::getAvatar)
                .update(AnswerEntity::getUsername)
                .where("user_id")
                .buildUpdate();
        baseDao.updateCommon(sql,avatar,username,id);
    }

    @Override
    public UserSettings selectById(String id) throws Exception {
        return getAnswerById(id);
    }

    public List<AnswerEntity> getAnswerByQuestionIdByPage(String questionId, int page, int size) throws Exception {
        String sql=new SQLBuilder("answer")
                .select("*")
                .limit(size)
                .offset((page-1)*size)
                .where("question_id")
                .buildSelect();
        return baseDao.selectByParams(sql,AnswerEntity.class,questionId);
    }
}
