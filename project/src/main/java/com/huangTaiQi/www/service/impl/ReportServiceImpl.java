package com.huangTaiQi.www.service.impl;

import com.huangTaiQi.www.dao.ReportAble;
import com.huangTaiQi.www.dao.impl.AnswerDao;
import com.huangTaiQi.www.dao.impl.CommentDao;
import com.huangTaiQi.www.dao.impl.QuestionDao;
import com.huangTaiQi.www.dao.impl.ReportDao;
import com.huangTaiQi.www.service.ReportService;
import com.huangTaiQi.www.utils.UserHolder;
import com.my_framework.www.annotation.Autowired;
import com.my_framework.www.annotation.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.huangTaiQi.www.constant.TypeConstants.*;

/**
 * @author 14629
 */
@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    QuestionDao questionDao;
    @Autowired
    AnswerDao answerDao;
    @Autowired
    CommentDao commentDao;
    @Autowired
    ReportDao reportDao;

    public void reportMessage(String type, String messageId, String content) throws SQLException {
        Map<String, ReportAble> reportDaoMap=new HashMap<>(3);
        reportDaoMap.put(ANSWER,answerDao);
        reportDaoMap.put(COMMENT,commentDao);
        reportDaoMap.put(QUESTION,questionDao);
        ReportAble reportAbleDao = reportDaoMap.get(type);
        Long reporterId = UserHolder.getUser().getId();
        //改变状态
        reportAbleDao.report(messageId,reporterId);
        //增加举报记录
        reportDao.addReport(reporterId,messageId,type,content);
    }
}
