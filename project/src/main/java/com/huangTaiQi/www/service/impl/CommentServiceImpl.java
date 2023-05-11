package com.huangTaiQi.www.service.impl;

import com.alibaba.fastjson.JSON;
import com.huangTaiQi.www.dao.impl.CommentDao;
import com.huangTaiQi.www.dao.impl.UserDao;
import com.huangTaiQi.www.model.dto.UserDTO;
import com.huangTaiQi.www.model.entity.CommentEntity;
import com.huangTaiQi.www.model.vo.CommentTree;
import com.huangTaiQi.www.model.vo.CommentTreeNode;
import com.huangTaiQi.www.service.CommentService;
import com.huangTaiQi.www.utils.UserHolder;
import com.my_framework.www.annotation.Autowired;
import com.my_framework.www.annotation.Service;
import com.my_framework.www.utils.CastUtil;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static com.huangTaiQi.www.constant.StateConstants.MESSAGE_CHECKED;

/**
 * @author 14629
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentDao commentDao;
    @Autowired
    UserDao userDao;
    @Override
    public void addComment(String content, String answerId, String pid) throws Exception {
        //若是前端可以传topId就没有这么麻烦，但是我前端不会
        String id=String.valueOf(UUID.randomUUID());
        String tid;
        UserDTO user = UserHolder.getUser();
        if("0".equals(pid)){
            //一级评论，tid=id
            tid=id;
        }else {
            //查询父评论的topId
            CommentEntity parentComment = getCommentById(pid);
            tid=parentComment.getTopId();
        }
        commentDao.addComment(id, user.getId(),user.getAvatar(), user.getUsername(),content,pid,tid,answerId,System.currentTimeMillis());
        //TODO:更新user
    }
    @Override
    public CommentEntity getCommentById(String id) throws Exception {
        return commentDao.getCommentById(id);
    }
    @Override
    public String getCommentTree(String answerId,String sortOrder) throws Exception {
        List<CommentEntity> commentByAnswerId = commentDao.getCommentByAnswerId(answerId);
        if(commentByAnswerId!=null){
            List<CommentTreeNode> commentTree = new CommentTree().createCommentTree(commentByAnswerId, sortOrder);
            return JSON.toJSONString(commentTree);
        }
        return JSON.toJSONString(null);
    }
    @Override
    public String getUncheckedComment(int page, int size) throws Exception {
        return JSON.toJSONString(commentDao.getCommentByState(page,size,MESSAGE_CHECKED));
    }
    @Override
    public void passComment(String id) throws SQLException {
        commentDao.updateCommentState(MESSAGE_CHECKED, CastUtil.castLong(id));
        UserDTO user = UserHolder.getUser();
        //TODO
        userDao.updateCommentCount(user.getId(),1);
    }
    @Override
    public int getCommentCountByState(int state) throws Exception {
        return commentDao.getCommentCountByState(state);
    }
}
