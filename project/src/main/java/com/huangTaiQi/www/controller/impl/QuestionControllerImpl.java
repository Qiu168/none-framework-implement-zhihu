package com.huangTaiQi.www.controller.impl;

import com.huangTaiQi.www.controller.BaseController;
import com.huangTaiQi.www.controller.IQuestionController;
import com.huangTaiQi.www.service.impl.QuestionServiceImpl;
import com.my_framework.www.annotation.*;

import javax.servlet.http.HttpServletResponse;


/**
 * @author 14629
 */
@Controller
@RequestMapping(value = "question")
public class QuestionControllerImpl extends BaseController implements IQuestionController {
    @Autowired
    QuestionServiceImpl questionService;
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


}
