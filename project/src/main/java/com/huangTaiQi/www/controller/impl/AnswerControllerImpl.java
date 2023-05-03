package com.huangTaiQi.www.controller.impl;

import com.huangTaiQi.www.controller.BaseController;
import com.huangTaiQi.www.controller.IAnswerController;
import com.huangTaiQi.www.service.impl.AnswerServiceImpl;
import com.my_framework.www.annotation.Autowired;
import com.my_framework.www.annotation.Controller;
import com.my_framework.www.annotation.RequestMapping;
import com.my_framework.www.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 14629
 */
@Controller
@RequestMapping("answer")
public class AnswerControllerImpl extends BaseController implements IAnswerController {
    @Autowired
    AnswerServiceImpl answerService;
    @RequestMapping
    public void getUserAnswer(@RequestParam("id") String userId, HttpServletResponse response) throws Exception {
        String json = answerService.getUserAnswer(userId);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(json);
    }
    @RequestMapping
    public void getAnswerByQuestionId(@RequestParam("id") String questionId,HttpServletResponse response) throws Exception {
        String answer = answerService.getAnswerByQuestionId(questionId);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(answer);
    }
    @RequestMapping
    public void getAnswerById(@RequestParam("answerId") String id,HttpServletResponse response) throws Exception {
        String answer = answerService.getAnswerById(id);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(answer);
    }
}
