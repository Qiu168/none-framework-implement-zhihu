package com.huangTaiQi.www.dao.impl;

import com.huangTaiQi.www.dao.BaseDao;
import com.huangTaiQi.www.dao.IQuestionDao;
import com.huangTaiQi.www.dao.ReportAble;
import com.huangTaiQi.www.dao.UpdateUserSettings;
import com.huangTaiQi.www.model.entity.AnswerEntity;
import com.huangTaiQi.www.model.entity.CategoryEntity;
import com.huangTaiQi.www.model.entity.QuestionEntity;
import com.huangTaiQi.www.utils.sql.SQLBuilder;
import com.my_framework.www.annotation.Repository;
import com.my_framework.www.pool.DataBaseUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.huangTaiQi.www.constant.StateConstants.MESSAGE_REPORTED;

/**
 * @author 14629
 */
@Repository
public class QuestionDao implements IQuestionDao , ReportAble, UpdateUserSettings {
    private final Connection connection = DataBaseUtil.getConnection();
    private final  BaseDao baseDao=new BaseDao(connection);


    @Override
    public int getQuestionCount() throws Exception {
        String sql=new SQLBuilder("question")
                .count("*").buildSelect();
        return baseDao.selectOne(sql, Integer.class);
    }

    @Override
    public int getQuestionCountByTitle(String title) throws Exception {
        String sql=new SQLBuilder("question")
                .count("*")
                .blurWhere("title")
                .buildSelect();
        return baseDao.selectOne(sql,Integer.class,"%"+title+"%");
    }
    @Override
    public int getQuestionCountByCategory(String categoryId) throws Exception {
        String sql=new SQLBuilder("question")
                .count("*")
                .where("category_id")
                .buildSelect();
        return baseDao.selectOne(sql,Integer.class,categoryId);
    }
    @Override
    public List<QuestionEntity> getQuestions(int page, int size) throws Exception {
        String sql=new SQLBuilder("question")
                .select("*")
                .limit(size)
                .offset((page-1)*size)
                .buildSelect();
        return baseDao.selectByParams(sql, QuestionEntity.class);
    }

    @Override
    public List<QuestionEntity> getQuestionsByTitle(int page, int size, String title) throws Exception {
        String sql=new SQLBuilder("question")
                .select("*")
                .limit(size)
                .offset((page-1)*size)
                .blurWhere("title")
                .buildSelect();
        String s = "%" + title + "%";
        return baseDao.selectByParams(sql, QuestionEntity.class,s);
    }
    @Override
    public List<QuestionEntity> getQuestionsByCategoryId(int page, int size, String categoryId) throws Exception {
        String sql=new SQLBuilder("question")
                .select("*")
                .limit(size)
                .offset((page-1)*size)
                .where("category_id")
                .buildSelect();
        return baseDao.selectByParams(sql, QuestionEntity.class,categoryId);
    }
    @Override
    public List<CategoryEntity> getCategory() throws Exception {
        String sql=new SQLBuilder("category")
                .select("*")
                .buildSelect();
        return baseDao.selectByParams(sql, CategoryEntity.class);
    }
    @Override
    public List<QuestionEntity> getQuestionByUser(int page, int size, Long userId) throws Exception {
        String sql =new SQLBuilder("question")
                .select("*")
                .limit(size)
                .offset((page-1)*size)
                .where("user_id")
                .buildSelect();
        return baseDao.selectByParams(sql, QuestionEntity.class,userId);
    }
    @Override
    public QuestionEntity getQuestionById(String id) throws Exception {
        String sql=new SQLBuilder("question")
                .select("*")
                .where("id")
                .buildSelect();
        return baseDao.selectOne(sql,QuestionEntity.class,id);
    }

    @Override
    public void addQuestion(String title, String content, String categoryId, String categoryName, String avatar, String username, Long uid) throws SQLException {
        String sql=new SQLBuilder("question")
                .insert("title")
                .insert("content")
                .insert("category_id")
                .insert("user_id")
                .insert("username")
                .insert("avatar")
                .insert("category_name")
                .buildInsert();
        baseDao.updateCommon(sql,title,content,categoryId,uid,username,avatar,categoryName);
    }
    @Override
    public void updateQuestionState(int state,Long id) throws SQLException {
        String sql=new SQLBuilder("question")
                .update(QuestionEntity::getState)
                .where("id")
                .buildUpdate();
        baseDao.updateCommon(sql,state,id);
    }
    @Override
    public List<QuestionEntity> getQuestionByIds(List<Long> ids) throws Exception {
        String sql=new SQLBuilder("question")
                .select("*")
                .whereIn("id",ids.size())
                .buildSelect();
        return baseDao.selectByParams(sql,QuestionEntity.class,ids.toArray());
    }
    @Override
    public List<QuestionEntity> getQuestionByState(int page, int size,int state) throws Exception {
        String sql=new SQLBuilder("question")
                .select("*")
                .limit(size)
                .offset((page-1)*size)
                .where("state")
                .buildSelect();
        return baseDao.selectByParams(sql,QuestionEntity.class,state);
    }
    @Override
    public int getQuestionCountByState(int state) throws Exception {
        String sql=new SQLBuilder("question")
                .count("*")
                .where("state")
                .buildSelect();
        return baseDao.selectOne(sql,Integer.class,state);
    }

    @Override
    public void report(String messageId, Long reporterId) throws SQLException {
        String sql=new SQLBuilder("question")
                .update(QuestionEntity::getState)
                .where("id")
                .buildUpdate();
        baseDao.updateCommon(sql,MESSAGE_REPORTED,messageId);
    }

    public void updateAnswerCount(Long questionId, int addCount) throws SQLException {
        String sql="UPDATE question SET answer_count = answer_count + "+addCount+" WHERE id = ?";
        baseDao.updateCommon(sql,questionId);
    }

    @Override
    public void updateSettings(Long id, String avatar, String username) throws SQLException {
        String sql=new SQLBuilder("question")
                .update(AnswerEntity::getAvatar)
                .update(AnswerEntity::getUsername)
                .where("user_id")
                .buildUpdate();
        baseDao.updateCommon(sql,avatar,username,id);
    }
}
