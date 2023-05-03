package com.huangTaiQi.www.service.impl;

import com.alibaba.fastjson.JSON;
import com.huangTaiQi.www.dao.impl.AnswerDao;
import com.huangTaiQi.www.dao.impl.QuestionDao;
import com.huangTaiQi.www.model.entity.AnswerEntity;
import com.huangTaiQi.www.service.QuestionService;
import com.my_framework.www.annotation.Autowired;
import com.my_framework.www.annotation.Service;

/**
 * @author 14629
 */
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionDao questionDao;
    @Autowired
    AnswerDao answerDao;
    @Override
    public int getQuestionCount() throws Exception {
        return questionDao.getQuestionCount();
    }
    @Override
    public int getQuestionCountByTitle(String title) throws Exception {
        return questionDao.getQuestionCountByTitle(title);
    }
    @Override
    public int getQuestionCountByCategory(String categoryId) throws Exception {
        return questionDao.getQuestionCountByCategory(categoryId);
    }
    @Override
    public String getQuestion(int page, int size) throws Exception {
        return JSON.toJSONString(questionDao.getQuestions(page,size));
    }
    @Override
    public String getQuestionByTitle(int page, int size, String title) throws Exception {
        return JSON.toJSONString(questionDao.getQuestionsByTitle(page,size,title));
    }
    @Override
    public String getQuestionByCategory(int page, int size, String categoryId) throws Exception {
        return JSON.toJSONString(questionDao.getQuestionsByCategoryId(page,size,categoryId));
    }
    @Override
    public String getCategory() throws Exception {
        return JSON.toJSONString(questionDao.getCategory());
    }
    @Override
    public String getQuestionByUser(int page, int size, Long userId) throws Exception {
        return JSON.toJSONString(questionDao.getQuestionByUser(page,size,userId));
    }

    @Override
    public String getQuestionById(String id) throws Exception {
        return JSON.toJSONString(questionDao.getQuestionById(id));
    }

    public String getQuestionByAnswerId(String answerId) throws Exception {
        AnswerEntity answer = answerDao.getAnswerById(answerId);
        return JSON.toJSONString(questionDao.getQuestionById(String.valueOf(answer.getQuestionId())));
    }
}
