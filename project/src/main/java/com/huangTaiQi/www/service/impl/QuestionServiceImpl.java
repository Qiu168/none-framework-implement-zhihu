package com.huangTaiQi.www.service.impl;

import com.alibaba.fastjson.JSON;
import com.huangTaiQi.www.dao.impl.AnswerDao;
import com.huangTaiQi.www.dao.impl.QuestionDao;
import com.huangTaiQi.www.dao.impl.UserDao;
import com.huangTaiQi.www.model.dto.UserDTO;
import com.huangTaiQi.www.model.entity.AnswerEntity;
import com.huangTaiQi.www.model.vo.IsSuccessVO;
import com.huangTaiQi.www.service.QuestionService;
import com.huangTaiQi.www.utils.ACFilter;
import com.huangTaiQi.www.utils.UserHolder;
import com.my_framework.www.annotation.Autowired;
import com.my_framework.www.annotation.Service;
import com.my_framework.www.utils.CastUtil;
import com.my_framework.www.utils.CollectionUtil;

import java.sql.SQLException;
import java.util.Set;

import static com.huangTaiQi.www.constant.SensitiveWordConstants.SENSITIVE_WORDS;
import static com.huangTaiQi.www.constant.StateConstants.MESSAGE_CHECKED;

/**
 * @author 14629
 */
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionDao questionDao;
    @Autowired
    AnswerDao answerDao;
    @Autowired
    UserDao userDao;
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
    @Override
    public String getQuestionByAnswerId(String answerId) throws Exception {
        AnswerEntity answer = answerDao.getAnswerById(answerId);
        return JSON.toJSONString(questionDao.getQuestionById(String.valueOf(answer.getQuestionId())));
    }
    @Override
    public String sendQuestion(String title, String content, String categoryId, String categoryName) throws SQLException {
        //自动检测是否有敏感词
        ACFilter acFilter=new ACFilter(SENSITIVE_WORDS);
        Set<String> sensitiveWords = acFilter.acMatch(title + "\n" + content);
        if(CollectionUtil.isNotEmpty(sensitiveWords)){
            //有铭感词返回信息
            return JSON.toJSONString(new IsSuccessVO(false,sensitiveWords.toString()));
        }
        UserDTO user = UserHolder.getUser();
        questionDao.addQuestion(title,content,categoryId,categoryName,user.getAvatar(),user.getUsername(),user.getId());
        return JSON.toJSONString(new IsSuccessVO(true,"发送成功"));
    }
    @Override
    public void passQuestion(String id) throws SQLException {
        questionDao.updateQuestionState(MESSAGE_CHECKED, CastUtil.castLong(id));
        UserDTO user = UserHolder.getUser();
        //TODO
        userDao.updateQuestionCount(user.getId(),1);
    }
    @Override
    public String getUncheckedQuestion(int page, int size) throws Exception {
        return JSON.toJSONString(questionDao.getQuestionByState(page,size,MESSAGE_CHECKED));
    }
    @Override
    public int getQuestionCountByState(int state) throws Exception {
        return questionDao.getQuestionCountByState(state);
    }
}
