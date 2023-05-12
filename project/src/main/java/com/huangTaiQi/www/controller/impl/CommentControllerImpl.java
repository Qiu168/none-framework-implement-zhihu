package com.huangTaiQi.www.controller.impl;

import com.alibaba.fastjson.JSON;
import com.huangTaiQi.www.controller.BaseController;
import com.huangTaiQi.www.controller.ICommentController;
import com.huangTaiQi.www.model.entity.CommentEntity;
import com.huangTaiQi.www.service.impl.CommentServiceImpl;
import com.my_framework.www.annotation.Autowired;
import com.my_framework.www.annotation.Controller;
import com.my_framework.www.annotation.RequestMapping;
import com.my_framework.www.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

import static com.huangTaiQi.www.constant.StateConstants.MESSAGE_CHECKING;

/**
 * @author 14629
 */
@Controller
@RequestMapping("comment")
public class CommentControllerImpl extends BaseController implements ICommentController {
    @Autowired
    CommentServiceImpl commentService;

    @Override
    @RequestMapping(method = "post")
    public void sendComment(@RequestParam("content") String content,
                            @RequestParam("answerId") String answerId,
                            @RequestParam("pid") String pid) throws Exception {
        commentService.addComment(content,answerId,pid);
    }
    @Override
    @RequestMapping
    public void getComment(@RequestParam("answerId") String answerId,@RequestParam("sortOrder") String sortOrder, HttpServletResponse response) throws Exception {
        String commentTree = commentService.getCommentTree(answerId, sortOrder);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(commentTree);
    }
    @Override
    @RequestMapping
    public void getUncheckedComment(@RequestParam("page") int page,@RequestParam("size") int size, HttpServletResponse response) throws Exception {
        String comments=commentService.getUncheckedComment(page,size);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(comments);
    }
    @Override
    @RequestMapping(value = "pass")
    public void passComment(@RequestParam("id")String id,@RequestParam("userId") Long uid) throws Exception {
        //TODO
        //改变question的state
        commentService.passComment(id);
    }
    @Override
    @RequestMapping
    public void getUncheckedTotal(HttpServletResponse response) throws Exception {
        int count=commentService.getCommentCountByState(MESSAGE_CHECKING);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(String.valueOf(count));
    }

    @Override
    @RequestMapping
    public void getCommentById(@RequestParam("id") String id, HttpServletResponse response) throws Exception {
        CommentEntity commentById = commentService.getCommentById(id);
        response.getWriter().write(JSON.toJSONString(commentById));
    }
}
