package com.huangTaiQi.www.dao.impl;

import com.huangTaiQi.www.dao.BaseDao;
import com.huangTaiQi.www.dao.IAnswerDao;
import com.huangTaiQi.www.model.vo.QuestionAnswer;
import com.huangTaiQi.www.utils.sql.SQLBuilder;
import com.my_framework.www.annotation.Repository;
import com.my_framework.www.pool.DataBaseUtil;

import java.sql.Connection;
import java.util.List;

/**
 * @author 14629
 */
@Repository
public class AnswerDao implements IAnswerDao {
    private final Connection connection = DataBaseUtil.getConnection();
    private final BaseDao baseDao=new BaseDao(connection);
    public List<QuestionAnswer> getUserAnswer(String userId) throws Exception {
        String sql=new SQLBuilder("answer A")
                .select("A.id","A.question_id","A.state","A.title","A.content","A.likes","B.title questionTitle")
                .join("question B","A.question_id=B.id")
                .where("A.user_id")
                .buildSelect();
        return baseDao.selectByParams(sql, QuestionAnswer.class, userId);
    }
}
