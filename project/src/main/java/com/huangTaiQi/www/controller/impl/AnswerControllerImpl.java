package com.huangTaiQi.www.controller.impl;

import com.alibaba.fastjson.JSON;
import com.huangTaiQi.www.constant.enums.MessageType;
import com.huangTaiQi.www.controller.BaseController;
import com.huangTaiQi.www.controller.IAnswerController;
import com.huangTaiQi.www.model.entity.AnswerEntity;
import com.huangTaiQi.www.service.impl.AnswerServiceImpl;
import com.huangTaiQi.www.service.impl.DynamicServiceImpl;
import com.my_framework.www.core.annotation.bean.Autowired;
import com.my_framework.www.core.annotation.stereotype.Controller;
import com.my_framework.www.webmvc.annotation.Pattern;
import com.my_framework.www.webmvc.annotation.RequestMapping;
import com.my_framework.www.webmvc.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.List;

import static com.huangTaiQi.www.constant.RegexConstants.NUMBER_REGEX;
import static com.huangTaiQi.www.constant.StateConstants.MESSAGE_CHECKING;
import static com.huangTaiQi.www.constant.StateConstants.MESSAGE_REPORTED;

/**
 * @author _qqiu
 */
@Controller
@RequestMapping("answer")
public class AnswerControllerImpl extends BaseController implements IAnswerController {
    @Autowired
    AnswerServiceImpl answerService;
    @Autowired
    DynamicServiceImpl dynamicService;
    @Override
    @RequestMapping
    public void getUserAnswer(@Pattern(regex = NUMBER_REGEX,message = "数字")@RequestParam("id") String userId,
                              HttpServletResponse response) throws Exception {
        String json = answerService.getUserAnswer(userId);
        response.getWriter().write(json);
    }
    @Override
    @RequestMapping
    public void getAnswerByQuestionId(@Pattern(regex = NUMBER_REGEX) @RequestParam("id") String questionId,
                                      HttpServletResponse response) throws Exception {
        String answer = answerService.getAnswerByQuestionId(questionId);
        response.getWriter().write(answer);
    }
    @Override
    @RequestMapping
    public void getAnswerByQuestionIdAndPage(@Pattern(regex = NUMBER_REGEX) @RequestParam("id") String questionId,
                                             @Pattern(regex = NUMBER_REGEX) @RequestParam("page") int page,
                                             @Pattern(regex = NUMBER_REGEX) @RequestParam("size") int size,
                                             HttpServletResponse response) throws Exception {
        List<AnswerEntity> answer = answerService.getAnswerByQuestionIdByPage(questionId, page, size);
        response.getWriter().write(JSON.toJSONString(answer));
    }
    @Override
    @RequestMapping
    public void getAnswerById(@Pattern(regex = NUMBER_REGEX)@RequestParam("answerId") String id,
                              HttpServletResponse response) throws Exception {
        String answer = answerService.getAnswerById(id);
        response.getWriter().write(answer);
    }
    @Override
    @RequestMapping(method = "post")
    public void sendAnswer(@Pattern @RequestParam("title") String title,
                           @Pattern @RequestParam("content") String content,
                           @Pattern(regex = NUMBER_REGEX) @RequestParam("questionId") String questionId,
                           HttpServletResponse response) throws Exception {
        String message = answerService.addAnswer(questionId, title, content);
        response.getWriter().write(message);
    }
    @Override
    @RequestMapping("pass")
    public void passAnswer(@Pattern(regex = NUMBER_REGEX)@RequestParam("id")String answerId,
                           @Pattern(regex = NUMBER_REGEX)@RequestParam("userId") Long id) throws Exception {
        //改变answer的state
        answerService.passAnswer(answerId);
        //发送动态
        dynamicService.sendDynamic(MessageType.ANSWER,id,answerId);
        //TODO：发消息给作者，
    }

    @Override
    @RequestMapping
    public void getUncheckedAnswer(@Pattern(regex = NUMBER_REGEX) @RequestParam("page") int page,
                                   @Pattern(regex = NUMBER_REGEX) @RequestParam("size") int size,
                                   HttpServletResponse response) throws Exception {
        String answers=answerService.getUncheckedAnswer(page,size);
        response.getWriter().write(answers);
    }
    @Override
    @RequestMapping
    public void getUncheckedTotal(HttpServletResponse response) throws Exception {
        int count=answerService.getAnswerCountByState(MESSAGE_CHECKING);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(String.valueOf(count));
    }
    @Override
    @RequestMapping
    public void getReportedAnswer(@Pattern(regex = NUMBER_REGEX) @RequestParam("page") int page,
                                  @Pattern(regex = NUMBER_REGEX) @RequestParam("size") int size,
                                  HttpServletResponse response) throws Exception {
        String question=answerService.getReportedAnswer(page,size);
        response.getWriter().write(question);
    }
    @Override
    @RequestMapping
    public void getReportedTotal(HttpServletResponse response) throws Exception {
        int count=answerService.getAnswerCountByState(MESSAGE_REPORTED);
        response.getWriter().write(String.valueOf(count));
    }
    @Override
    @RequestMapping
    public void passReported(@Pattern(regex = NUMBER_REGEX) @RequestParam("id") String answerId,
                             @Pattern(regex = NUMBER_REGEX) @RequestParam("userId") Long id,
                             @Pattern(regex = NUMBER_REGEX) @RequestParam("intentional") String intentional) throws SQLException {
        answerService.passReportedAnswer(answerId,intentional);

    }
}
