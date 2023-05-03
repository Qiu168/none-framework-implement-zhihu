package com.huangTaiQi.www.dao.impl;

import com.huangTaiQi.www.dao.BaseDao;
import com.huangTaiQi.www.dao.IQuestionDao;
import com.huangTaiQi.www.model.entity.CategoryEntity;
import com.huangTaiQi.www.model.entity.QuestionEntity;
import com.huangTaiQi.www.utils.sql.SQLBuilder;
import com.my_framework.www.annotation.Repository;
import com.my_framework.www.pool.DataBaseUtil;

import java.sql.Connection;
import java.util.List;

/**
 * @author 14629
 */
@Repository
public class QuestionDao implements IQuestionDao {
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
}
