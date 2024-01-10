package com.huangTaiQi.www.controller.impl;

import com.alibaba.fastjson.JSON;
import com.huangTaiQi.www.controller.BaseController;
import com.huangTaiQi.www.controller.ICommentController;
import com.huangTaiQi.www.model.entity.CommentEntity;
import com.huangTaiQi.www.model.vo.IsSuccessVO;
import com.huangTaiQi.www.service.impl.CommentServiceImpl;
import com.my_framework.www.core.annotation.bean.Autowired;
import com.my_framework.www.core.annotation.stereotype.Controller;
import com.my_framework.www.webmvc.annotation.Pattern;
import com.my_framework.www.webmvc.annotation.RequestMapping;
import com.my_framework.www.webmvc.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

import java.sql.SQLException;

import static com.huangTaiQi.www.constant.RegexConstants.NUMBER_REGEX;
import static com.huangTaiQi.www.constant.StateConstants.MESSAGE_CHECKING;
import static com.huangTaiQi.www.constant.StateConstants.MESSAGE_REPORTED;

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
    public void sendComment(@Pattern @RequestParam("content") String content,
                            @Pattern(regex = NUMBER_REGEX) @RequestParam("answerId") String answerId,
                            @Pattern @RequestParam("pid") String pid,
                            HttpServletResponse response) throws Exception {
        IsSuccessVO isSuccessVO = commentService.addComment(content, answerId, pid);
        response.getWriter().write(JSON.toJSONString(isSuccessVO));
    }
    @Override
    @RequestMapping
    public void getComment(@Pattern(regex = NUMBER_REGEX) @RequestParam("answerId") String answerId,
                           @RequestParam("sortOrder") String sortOrder,
                           HttpServletResponse response) throws Exception {
        String commentTree = commentService.getCommentTree(answerId, sortOrder);
        response.getWriter().write(commentTree);
    }
    @Override
    @RequestMapping
    public void getUncheckedComment(@Pattern(regex = NUMBER_REGEX) @RequestParam("page") int page,
                                    @Pattern(regex = NUMBER_REGEX) @RequestParam("size") int size,
                                    HttpServletResponse response) throws Exception {
        String comments=commentService.getUncheckedComment(page,size);
        response.getWriter().write(comments);
    }
    @Override
    @RequestMapping(value = "pass")
    public void passComment(@Pattern @RequestParam("id")String id,
                            @Pattern(regex = NUMBER_REGEX) @RequestParam("userId") Long uid) throws Exception {
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
    public void getCommentById(@Pattern @RequestParam("id") String id,
                               HttpServletResponse response) throws Exception {
        CommentEntity commentById = commentService.getCommentById(id);
        response.getWriter().write(JSON.toJSONString(commentById));
    }
    @Override
    @RequestMapping
    public void getReportedComment(@Pattern(regex = NUMBER_REGEX) @RequestParam("page") int page,
                                   @Pattern(regex = NUMBER_REGEX) @RequestParam("size") int size,
                                   HttpServletResponse response) throws Exception {
        String comments=commentService.getReportedComment(page,size);
        response.getWriter().write(comments);
    }
    @Override
    @RequestMapping
    public void getReportedTotal(HttpServletResponse response) throws Exception {
        int count=commentService.getCommentCountByState(MESSAGE_REPORTED);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(String.valueOf(count));
    }
    @Override
    @RequestMapping
    public void passReported(@Pattern @RequestParam("id")String commentId,
                             @Pattern(regex = NUMBER_REGEX) @RequestParam("userId") Long id,
                             @Pattern(regex = NUMBER_REGEX) @RequestParam("intentional") String intentional) throws SQLException {
        commentService.passReportedComment(commentId,intentional);
    }
}
