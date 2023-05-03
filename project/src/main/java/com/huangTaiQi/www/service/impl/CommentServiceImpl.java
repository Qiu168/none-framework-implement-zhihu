package com.huangTaiQi.www.service.impl;

import com.alibaba.fastjson.JSON;
import com.huangTaiQi.www.dao.impl.CommentDao;
import com.huangTaiQi.www.model.entity.CommentEntity;
import com.huangTaiQi.www.model.vo.CommentTree;
import com.huangTaiQi.www.model.vo.CommentTreeNode;
import com.huangTaiQi.www.service.CommentService;
import com.my_framework.www.annotation.Autowired;
import com.my_framework.www.annotation.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * @author 14629
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentDao commentDao;

    public void addComment(String content, String pid,String tid) throws SQLException {
        commentDao.addComment(content,pid,tid);
    }
    public CommentEntity getCommentById(String id) throws Exception {
        return commentDao.getCommentById(id);
    }

    public String getCommentTree(String answerId,String sortOrder) throws Exception {
        List<CommentEntity> commentByAnswerId = commentDao.getCommentByAnswerId(answerId);
        List<CommentTreeNode> commentTree = new CommentTree().createCommentTree(commentByAnswerId, sortOrder);
        return JSON.toJSONString(commentTree);
    }
}
