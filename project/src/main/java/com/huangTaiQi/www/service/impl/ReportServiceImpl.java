package com.huangTaiQi.www.service.impl;

import com.huangTaiQi.www.dao.ReportAble;
import com.huangTaiQi.www.dao.impl.AnswerDao;
import com.huangTaiQi.www.dao.impl.CommentDao;
import com.huangTaiQi.www.dao.impl.QuestionDao;
import com.huangTaiQi.www.dao.impl.ReportDao;
import com.huangTaiQi.www.service.ReportService;
import com.huangTaiQi.www.utils.UserHolder;
import com.my_framework.www.core.annotation.bean.Autowired;
import com.my_framework.www.core.annotation.stereotype.Service;

import java.sql.SQLException;

import static com.huangTaiQi.www.constant.TypeConstants.*;

/**
 * @author _qqiu
 */
@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private AnswerDao answerDao;
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private ReportDao reportDao;

    private ReportAble getReportableDao(String type){
        switch (type) {
            case ANSWER:
                return answerDao;
            case COMMENT:
                return commentDao;
            case QUESTION:
                return questionDao;
            default:
                throw new IllegalArgumentException(type + " is not a valid reportable object type.");
        }
    }

    @Override
    public void reportMessage(String type, String messageId, String content) throws SQLException {
        ReportAble reportAbleDao = getReportableDao(type);
        Long reporterId = UserHolder.getUser().getId();
        //改变状态
        reportAbleDao.report(messageId,reporterId);
        //增加举报记录
        reportDao.addReport(reporterId,messageId,type,content);
    }
}
