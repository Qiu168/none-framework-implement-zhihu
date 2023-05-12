package com.huangTaiQi.www.controller.impl;

import com.huangTaiQi.www.constant.enums.MessageType;
import com.huangTaiQi.www.controller.BaseController;
import com.huangTaiQi.www.controller.IQuestionController;
import com.huangTaiQi.www.service.impl.DynamicServiceImpl;
import com.huangTaiQi.www.service.impl.QuestionServiceImpl;
import com.my_framework.www.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.huangTaiQi.www.constant.StateConstants.MESSAGE_CHECKING;


/**
 * @author 14629
 */
@Controller
@RequestMapping(value = "question")
public class QuestionControllerImpl extends BaseController implements IQuestionController {
    @Autowired
    QuestionServiceImpl questionService;
    @Autowired
    DynamicServiceImpl dynamicService;
    @Override
    @RequestMapping
    public void getQuestionCount(HttpServletResponse response) throws Exception {
        int count=questionService.getQuestionCount();
        response.setContentType("text/html;charset=utf-8");
        System.out.println("count="+count);
        response.getWriter().write(String.valueOf(count));
    }

    @Override
    @RequestMapping
    public void getQuestionCountByTitle(@Pattern @RequestParam("title") String title, HttpServletResponse response) throws Exception {
        int count=questionService.getQuestionCountByTitle(title);
        response.setContentType("text/html;charset=utf-8");
        System.out.println("count="+count);
        response.getWriter().write(String.valueOf(count));
    }

    @Override
    @RequestMapping
    public void getQuestionCountByCategory(@RequestParam("categoryId") String categoryId, HttpServletResponse response) throws Exception {
        int count=questionService.getQuestionCountByCategory(categoryId);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(String.valueOf(count));
    }

    @Override
    @RequestMapping
    public void getQuestion(@RequestParam("page") int page,@RequestParam("size") int size, HttpServletResponse response) throws Exception {
        String questions=questionService.getQuestion(page,size);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(questions);
    }

    @Override
    @RequestMapping
    public void getQuestionByTitle( @RequestParam("title") String title,@RequestParam("page") int page,@RequestParam("size") int size, HttpServletResponse response) throws Exception {
        String questions=questionService.getQuestionByTitle(page ,size,title);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(questions);
    }

    @Override
    @RequestMapping
    public void getQuestionByCategory(@RequestParam("categoryId") String categoryId,@RequestParam("page") int page,@RequestParam("size") int size, HttpServletResponse response) throws Exception {
        String questions=questionService.getQuestionByCategory(page ,size,categoryId);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(questions);
    }
    @Override
    @RequestMapping
    public void getQuestionByUser(@RequestParam("userId") Long userId, @RequestParam("page") int page, @RequestParam("size") int size, HttpServletResponse response) throws Exception {
        String questions=questionService.getQuestionByUser(page ,size,userId);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(questions);
    }
    @Override
    @RequestMapping
    public void getCategory(HttpServletResponse response) throws Exception {
        String category=questionService.getCategory();
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(category);
    }

    @Override
    @RequestMapping
    public void getQuestionById(@RequestParam("id") String id, HttpServletResponse response) throws Exception {
        String question = questionService.getQuestionById(id);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(question);
    }
    @Override
    @RequestMapping
    public void getQuestionByAnswerId(@RequestParam("answerId") String answerId, HttpServletResponse response) throws Exception {
        String question = questionService.getQuestionByAnswerId(answerId);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(question);
    }
    @Override
    @RequestMapping(method = "post")
    public void sendQuestion(@RequestParam("title") String title,
                             @RequestParam("categoryId") String categoryId,
                             @RequestParam("categoryName") String categoryName,
                             @RequestParam("content") String content,
                             HttpServletResponse response) throws SQLException, IOException {
        String json = questionService.sendQuestion(title, content, categoryId, categoryName);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(json);
    }
    @Override
    @RequestMapping("pass")
    public void passQuestion(@RequestParam("id")String questionId,@RequestParam("userId") Long id) throws Exception {
        //TODO
        //改变question的state
        questionService.passQuestion(questionId);
        //发送动态
        dynamicService.sendDynamic(MessageType.QUESTION,id,questionId);
    }
    @Access(rightName = 6L)
    @Override
    @RequestMapping
    public void getUncheckedQuestion(@RequestParam("page") int page,@RequestParam("size") int size, HttpServletResponse response) throws Exception {
        String questions=questionService.getUncheckedQuestion(page,size);
        response.getWriter().write(questions);
    }
    @Access(rightName = 6L)
    @Override
    @RequestMapping
    public void getUncheckedTotal(HttpServletResponse response) throws Exception {
        int count=questionService.getQuestionCountByState(MESSAGE_CHECKING);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(String.valueOf(count));
    }
}
