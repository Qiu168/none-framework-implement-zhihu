package com.huangTaiQi.www.controller.impl;

import com.huangTaiQi.www.controller.BaseController;
import com.huangTaiQi.www.controller.ICommentController;
import com.huangTaiQi.www.model.entity.CommentEntity;
import com.huangTaiQi.www.service.impl.CommentServiceImpl;
import com.my_framework.www.annotation.Autowired;
import com.my_framework.www.annotation.Controller;
import com.my_framework.www.annotation.RequestMapping;
import com.my_framework.www.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 14629
 */
@Controller
@RequestMapping("comment")
public class CommentControllerImpl extends BaseController implements ICommentController {
    @Autowired
    CommentServiceImpl commentService;

    @RequestMapping(method = "post")
    public void sendComment(@RequestParam("content") String content,@RequestParam("pid") String pid) throws Exception {
        String tid;
        //若是前端可以传topId就没有这么麻烦，但是我前端不会
        if("0".equals(pid)){
            //如果插入的是一级评论，tid为自己的id
            tid=" LAST_INSERT_ID()";
        }else {
            //查询父评论的topId
            CommentEntity parentComment = commentService.getCommentById(pid);
            tid=String.valueOf(parentComment.getTopId());
        }
        commentService.addComment(content,pid,tid);
    }
    @RequestMapping
    public void getComment(@RequestParam("answerId") String answerId,@RequestParam("sortOrder") String sortOrder, HttpServletResponse response) throws Exception {
        String commentTree = commentService.getCommentTree(answerId, sortOrder);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(commentTree);
    }
}
