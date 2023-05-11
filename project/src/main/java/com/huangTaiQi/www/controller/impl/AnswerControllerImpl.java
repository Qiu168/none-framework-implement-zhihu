package com.huangTaiQi.www.controller.impl;

import com.huangTaiQi.www.constant.enums.MessageType;
import com.huangTaiQi.www.controller.BaseController;
import com.huangTaiQi.www.controller.IAnswerController;
import com.huangTaiQi.www.service.impl.AnswerServiceImpl;
import com.huangTaiQi.www.service.impl.DynamicServiceImpl;
import com.my_framework.www.annotation.Autowired;
import com.my_framework.www.annotation.Controller;
import com.my_framework.www.annotation.RequestMapping;
import com.my_framework.www.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.huangTaiQi.www.constant.StateConstants.MESSAGE_CHECKING;

/**
 * @author 14629
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
    public void getUserAnswer(@RequestParam("id") String userId, HttpServletResponse response) throws Exception {
        String json = answerService.getUserAnswer(userId);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(json);
    }
    @Override
    @RequestMapping
    public void getAnswerByQuestionId(@RequestParam("id") String questionId,HttpServletResponse response) throws Exception {
        String answer = answerService.getAnswerByQuestionId(questionId);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(answer);
    }
    @Override
    @RequestMapping
    public void getAnswerById(@RequestParam("answerId") String id,HttpServletResponse response) throws Exception {
        String answer = answerService.getAnswerById(id);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(answer);
    }
    @Override
    @RequestMapping(method = "post")
    public void sendAnswer(@RequestParam("title") String title,
                           @RequestParam("content") String content,
                           @RequestParam("questionId") String questionId,
                           HttpServletResponse response) throws SQLException, IOException {
        String message = answerService.addAnswer(questionId, title, content);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(message);
    }
    @Override
    @RequestMapping("pass")
    public void passAnswer(@RequestParam("answerId")String answerId,@RequestParam("userId") Long id) throws Exception {
        //TODO
        //改变question的state
        answerService.passAnswer(answerId);
        //发送动态
        dynamicService.sendDynamic(MessageType.ANSWER,id,answerId);
    }

    @Override
    @RequestMapping
    public void getUncheckedAnswer(@RequestParam("page") int page,@RequestParam("size") int size, HttpServletResponse response) throws Exception {
        String answers=answerService.getUncheckedAnswer(page,size);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(answers);
    }
    @Override
    @RequestMapping
    public void getUncheckedTotal(HttpServletResponse response) throws Exception {
        int count=answerService.getAnswerCountByState(MESSAGE_CHECKING);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(String.valueOf(count));
    }
}
