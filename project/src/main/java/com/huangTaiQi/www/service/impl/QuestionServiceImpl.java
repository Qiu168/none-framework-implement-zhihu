package com.huangTaiQi.www.service.impl;

import com.alibaba.fastjson.JSON;
import com.huangTaiQi.www.dao.impl.AnswerDao;
import com.huangTaiQi.www.dao.impl.QuestionDao;
import com.huangTaiQi.www.dao.impl.ReportDao;
import com.huangTaiQi.www.dao.impl.UserDao;
import com.huangTaiQi.www.helper.UpdateUserSettingsHelper;
import com.huangTaiQi.www.model.dto.UserDTO;
import com.huangTaiQi.www.model.entity.AnswerEntity;
import com.huangTaiQi.www.model.entity.QuestionEntity;
import com.huangTaiQi.www.model.vo.IsSuccessVO;
import com.huangTaiQi.www.service.QuestionService;
import com.huangTaiQi.www.utils.ACFilter;
import com.huangTaiQi.www.utils.UserHolder;
import com.my_framework.www.core.annotation.bean.Autowired;
import com.my_framework.www.core.annotation.stereotype.Service;
import com.my_framework.www.utils.CastUtil;
import com.my_framework.www.utils.CollectionUtil;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import static com.huangTaiQi.www.constant.SensitiveWordConstants.SENSITIVE_WORDS;
import static com.huangTaiQi.www.constant.StateConstants.*;
import static com.huangTaiQi.www.constant.TypeConstants.QUESTION;

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
    @Autowired
    ReportDao reportDao;
    @Autowired
    UpdateUserSettingsHelper updateUserSettingsHelper;
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
        List<QuestionEntity> questions = questionDao.getQuestions(page, size);
        updateUserSettingsHelper.checkUserSettings(QUESTION,questions);
        return JSON.toJSONString(questions);
    }
    @Override
    public String getQuestionByTitle(int page, int size, String title) throws Exception {
        List<QuestionEntity> questionsByTitle = questionDao.getQuestionsByTitle(page, size, title);
        updateUserSettingsHelper.checkUserSettings(QUESTION,questionsByTitle);
        return JSON.toJSONString(questionsByTitle);
    }
    @Override
    public String getQuestionByCategory(int page, int size, String categoryId) throws Exception {
        List<QuestionEntity> questionsByCategoryId = questionDao.getQuestionsByCategoryId(page, size, categoryId);
        updateUserSettingsHelper.checkUserSettings(QUESTION,questionsByCategoryId);
        return JSON.toJSONString(questionsByCategoryId);
    }
    @Override
    public String getCategory() throws Exception {
        return JSON.toJSONString(questionDao.getCategory());
    }
    @Override
    public String getQuestionByUser(int page, int size, Long userId) throws Exception {
        List<QuestionEntity> questionByUser = questionDao.getQuestionByUser(page, size, userId);
        updateUserSettingsHelper.checkUserSettings(QUESTION,questionByUser);
        return JSON.toJSONString(questionByUser);
    }

    @Override
    public String getQuestionById(String id) throws Exception {
        QuestionEntity questionById = questionDao.getQuestionById(id);
        updateUserSettingsHelper.checkUserSettings(QUESTION,questionById);
        return JSON.toJSONString(questionById);
    }
    @Override
    public String getQuestionByAnswerId(String answerId) throws Exception {
        AnswerEntity answer = answerDao.getAnswerById(answerId);
        QuestionEntity questionById = questionDao.getQuestionById(String.valueOf(answer.getQuestionId()));
        updateUserSettingsHelper.checkUserSettings(QUESTION,questionById);
        return JSON.toJSONString(questionById);
    }
    @Override
    public String sendQuestion(String title, String content, String categoryId, String categoryName) throws SQLException {
        //自动检测是否有敏感词
        ACFilter acFilter=new ACFilter(SENSITIVE_WORDS);
        Set<String> sensitiveWords = acFilter.acMatch(title + "\n" + content);
        if(CollectionUtil.isNotEmpty(sensitiveWords)){
            //有铭感词返回信息
            return JSON.toJSONString(new IsSuccessVO(false,sensitiveWords.toString()));
            //TODO：发信息
        }
        UserDTO user = UserHolder.getUser();
        questionDao.addQuestion(title,content,categoryId,categoryName,user.getAvatar(),user.getUsername(),user.getId());
        return JSON.toJSONString(new IsSuccessVO(true,"发送成功"));
    }
    @Override
    public void passQuestion(String id) throws SQLException {
        questionDao.updateQuestionState(MESSAGE_CHECKED, CastUtil.castLong(id));
        UserDTO user = UserHolder.getUser();
        userDao.updateQuestionCount(user.getId(),1);
    }
    @Override
    public String getUncheckedQuestion(int page, int size) throws Exception {
        List<QuestionEntity> questionByState = questionDao.getQuestionByState(page, size, MESSAGE_CHECKING);
        updateUserSettingsHelper.checkUserSettings(QUESTION,questionByState);
        return JSON.toJSONString(questionByState);
    }
    @Override
    public int getQuestionCountByState(int state) throws Exception {
        return questionDao.getQuestionCountByState(state);
    }
    @Override
    public String getReportedQuestion(int page, int size) throws Exception {
        List<QuestionEntity> questionReported = questionDao.getQuestionByState(page, size, MESSAGE_REPORTED);
        return JSON.toJSONString(questionReported);
    }
    @Override
    public void passReportedQuestion(String questionId, String intentional) throws SQLException {
        questionDao.updateQuestionState(MESSAGE_CHECKED, CastUtil.castLong(questionId));
        //TODO:
        reportDao.updateLegal(intentional,questionId,QUESTION);
    }
}
