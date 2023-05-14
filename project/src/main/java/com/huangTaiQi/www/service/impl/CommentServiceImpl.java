package com.huangTaiQi.www.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.huangTaiQi.www.dao.impl.*;
import com.huangTaiQi.www.helper.CheckBlackListHelper;
import com.huangTaiQi.www.helper.UpdateUserSettingsHelper;
import com.huangTaiQi.www.model.dto.UserDTO;
import com.huangTaiQi.www.model.entity.CommentEntity;
import com.huangTaiQi.www.model.vo.CommentTree;
import com.huangTaiQi.www.model.vo.CommentTreeNode;
import com.huangTaiQi.www.model.vo.IsSuccessVO;
import com.huangTaiQi.www.service.CommentService;
import com.huangTaiQi.www.utils.UserHolder;
import com.my_framework.www.annotation.Autowired;
import com.my_framework.www.annotation.Service;
import com.my_framework.www.redis.LIRSCache;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import static com.huangTaiQi.www.constant.JedisConstants.REDIS_CACHE_CAPACITY;
import static com.huangTaiQi.www.constant.StateConstants.*;
import static com.huangTaiQi.www.constant.TypeConstants.COMMENT;

/**
 * @author 14629
 */
@Service
public class CommentServiceImpl implements CommentService {
    Logger logger= Logger.getLogger(CommentServiceImpl.class.getName());
    @Autowired
    CommentDao commentDao;
    @Autowired
    UserDao userDao;
    @Autowired
    AnswerDao answerDao;
    @Autowired
    BlackListDao blackListDao;
    @Autowired
    ReportDao reportDao;
    @Autowired
    UpdateUserSettingsHelper updateUserSettingsHelper;
    LIRSCache lirsCache=LIRSCache.getInstance(REDIS_CACHE_CAPACITY);
    @Override
    public IsSuccessVO addComment(String content, String answerId, String pid) throws Exception {
        IsSuccessVO isSuccessVO = CheckBlackListHelper.checkBlackList(blackListDao, answerDao, answerId);
        if(isSuccessVO!=null){
            return isSuccessVO;
        }
        UserDTO user = UserHolder.getUser();
        //若是前端可以传topId就没有这么麻烦，但是我前端不会
        String id=String.valueOf(UUID.randomUUID());
        String tid;
        if("0".equals(pid)){
            //一级评论，tid=id
            tid=id;
        }else {
            //查询父评论的topId
            CommentEntity parentComment = getCommentById(pid);
            tid=parentComment.getTopId();
        }
        commentDao.addComment(id, user.getId(),user.getAvatar(), user.getUsername(),content,pid,tid,answerId,System.currentTimeMillis());
        //删除缓存
        String cacheKey = "answer:" + answerId + ":comments";
        lirsCache.delete(cacheKey);
        return new IsSuccessVO(true,"success");
    }
    @Override
    public CommentEntity getCommentById(String id) throws Exception {
        CommentEntity commentById = commentDao.getCommentById(id);
        updateUserSettingsHelper.checkUserSettings(COMMENT,commentById);
        return commentById;
    }
    @Override
    public String getCommentTree(String answerId,String sortOrder) throws Exception {
        List<CommentEntity> commentByAnswerId;
        String cacheKey = "answer:" + answerId + ":comments";
        String cacheValue = lirsCache.get(cacheKey);
        if (cacheValue != null) {
            // 如果缓存中存在，则反序列化JSON为CommentEntity对象
            commentByAnswerId=JSON.parseObject(cacheValue, new TypeReference<List<CommentEntity>>(){});
        } else {
            // 如果缓存中不存在，则从数据库获取评论
            commentByAnswerId = commentDao.getCommentByAnswerId(answerId);
            // 将评论序列化为JSON字符串，并存入缓存
            if (commentByAnswerId != null) {
                String jsonString = JSON.toJSONString(commentByAnswerId);
                lirsCache.put(cacheKey,jsonString);
            }
        }
        updateUserSettingsHelper.checkUserSettings(COMMENT,commentByAnswerId);
        if(commentByAnswerId!=null){
            List<CommentTreeNode> commentTree = new CommentTree().createCommentTree(commentByAnswerId, sortOrder);
            return JSON.toJSONString(commentTree);
        }
        return JSON.toJSONString(null);
    }
    @Override
    public String getUncheckedComment(int page, int size) throws Exception {
        List<CommentEntity> commentByState = commentDao.getCommentByState(page, size, MESSAGE_CHECKING);
        updateUserSettingsHelper.checkUserSettings(COMMENT,commentByState);
        return JSON.toJSONString(commentByState);
    }
    @Override
    public void passComment(String id) throws SQLException {
        commentDao.updateCommentState(MESSAGE_CHECKED, id);
        UserDTO user = UserHolder.getUser();
        userDao.updateCommentCount(user.getId(),1);
        //TODO:给作者和回复的人发消息
    }
    @Override
    public int getCommentCountByState(int state) throws Exception {
        return commentDao.getCommentCountByState(state);
    }

    public String getReportedComment(int page, int size) throws Exception {
        List<CommentEntity> commentByState = commentDao.getCommentByState(page, size, MESSAGE_REPORTED);
        updateUserSettingsHelper.checkUserSettings(COMMENT,commentByState);
        return JSON.toJSONString(commentByState);
    }

    public void passReportedComment(String commentId, String intentional) throws SQLException {
        commentDao.updateCommentState(MESSAGE_CHECKED, commentId);
        //todo:
        reportDao.updateLegal(intentional,commentId,COMMENT);
        //todo:给举报人发信息
    }
}
