package com.huangTaiQi.www.service.impl;

import com.alibaba.fastjson.JSON;
import com.huangTaiQi.www.dao.impl.AnswerDao;
import com.huangTaiQi.www.service.AnswerService;
import com.my_framework.www.annotation.Autowired;
import com.my_framework.www.annotation.Service;

import java.util.List;

/**
 * @author 14629
 */
@Service
public class AnswerServiceImpl implements AnswerService {
    @Autowired
    AnswerDao answerDao;
    public String getUserAnswer(String userId) throws Exception {
        return JSON.toJSONString(answerDao.getUserAnswer(userId));
    }

    public String getAnswerByQuestionId(String questionId) throws Exception {
        return JSON.toJSONString(answerDao.getAnswerByQuestionId(questionId));
    }

    public String getAnswerById(String id) throws Exception {
        return JSON.toJSONString(answerDao.getAnswerById(id));
    }
}
