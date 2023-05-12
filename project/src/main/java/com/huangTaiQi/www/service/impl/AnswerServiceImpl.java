package com.huangTaiQi.www.service.impl;

import com.alibaba.fastjson.JSON;
import com.huangTaiQi.www.dao.UpdateUserSettings;
import com.huangTaiQi.www.dao.impl.AnswerDao;
import com.huangTaiQi.www.dao.impl.QuestionDao;
import com.huangTaiQi.www.dao.impl.UserDao;
import com.huangTaiQi.www.helper.UpdateUserSettingsHelper;
import com.huangTaiQi.www.model.UserSettings;
import com.huangTaiQi.www.model.dto.UserDTO;
import com.huangTaiQi.www.model.entity.AnswerEntity;
import com.huangTaiQi.www.model.vo.IsSuccessVO;
import com.huangTaiQi.www.service.AnswerService;
import com.huangTaiQi.www.utils.ACFilter;
import com.huangTaiQi.www.utils.UserHolder;
import com.my_framework.www.annotation.Autowired;
import com.my_framework.www.annotation.Service;
import com.my_framework.www.utils.CastUtil;
import com.my_framework.www.utils.CollectionUtil;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import static com.huangTaiQi.www.constant.SensitiveWordConstants.SENSITIVE_WORDS;
import static com.huangTaiQi.www.constant.StateConstants.MESSAGE_CHECKED;
import static com.huangTaiQi.www.constant.StateConstants.MESSAGE_CHECKING;
import static com.huangTaiQi.www.constant.TypeConstants.ANSWER;

/**
 * @author 14629
 */
@Service
public class AnswerServiceImpl implements AnswerService {
    @Autowired
    AnswerDao answerDao;
    @Autowired
    UserDao userDao;
    @Autowired
    QuestionDao questionDao;
    @Autowired
    UpdateUserSettingsHelper updateUserSettingsHelper;
    @Override
    public String getUserAnswer(String userId) throws Exception {
        return JSON.toJSONString(answerDao.getUserAnswer(userId));
    }
    @Override
    public String getAnswerByQuestionId(String questionId) throws Exception {
        List<AnswerEntity> answer = answerDao.getAnswerByQuestionId(questionId);
        updateUserSettingsHelper.checkUserSettings(ANSWER,answer);
        return JSON.toJSONString(answer);
    }
    @Override
    public String getAnswerById(String id) throws Exception {
        AnswerEntity answer = answerDao.getAnswerById(id);
        updateUserSettingsHelper.checkUserSettings(ANSWER,answer);
        return JSON.toJSONString(answer);
    }
    @Override
    public String addAnswer(String questionId, String title, String content) throws SQLException {
        //自动检测是否有敏感词
        ACFilter acFilter=new ACFilter(SENSITIVE_WORDS);
        Set<String> sensitiveWords = acFilter.acMatch(title + " \n" + content);
        if(CollectionUtil.isNotEmpty(sensitiveWords)){
            //有敏感词返回信息
            return JSON.toJSONString(new IsSuccessVO(false,sensitiveWords.toString()));
        }
        UserDTO user = UserHolder.getUser();
        answerDao.addAnswer(user.getId(),user.getAvatar(),user.getUsername(),title,content,questionId);
        return JSON.toJSONString(new IsSuccessVO(true,"发送成功"));
    }

    @Override
    public void passAnswer(String id) throws Exception {
        //更新状态
        answerDao.updateAnswerState(MESSAGE_CHECKED, CastUtil.castLong(id));
        UserDTO user = UserHolder.getUser();
        //获取answer
        AnswerEntity answerById = answerDao.getAnswerById(id);
        //更新user的问题数
        userDao.updateQuestionCount(user.getId(),1);
        //更新问题的回答数
        questionDao.updateAnswerCount(answerById.getQuestionId(),1);
    }
    @Override
    public String getUncheckedAnswer(int page, int size) throws Exception {
        List<AnswerEntity> answer = answerDao.getAnswerByState(page, size, MESSAGE_CHECKING);
        updateUserSettingsHelper.checkUserSettings(ANSWER,answer);
        return JSON.toJSONString(answer);
    }
    @Override
    public int getAnswerCountByState(int state) throws Exception {
        return answerDao.getAnswerCountByState(state);
    }

}
