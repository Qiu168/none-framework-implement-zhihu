package com.huangTaiQi.www.service.impl;

import com.alibaba.fastjson.JSON;
import com.huangTaiQi.www.dao.impl.AnswerDao;
import com.huangTaiQi.www.dao.impl.LikeDao;
import com.huangTaiQi.www.model.dto.UserDTO;
import com.huangTaiQi.www.model.entity.LikeEntity;
import com.huangTaiQi.www.model.vo.IsSuccessVO;
import com.huangTaiQi.www.service.LikeService;
import com.huangTaiQi.www.utils.UserHolder;
import com.my_framework.www.core.annotation.bean.Autowired;
import com.my_framework.www.core.annotation.stereotype.Service;

/**
 * @author 14629
 */
@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    LikeDao likeDao;
    @Autowired
    AnswerDao answerDao;

    @Override
    public String isLike(String answerId) throws Exception {
        UserDTO user = UserHolder.getUser();
        LikeEntity likeEntity = null;
        if(user!=null){
            //登陆了
            likeEntity=likeDao.selectLike(user.getId(),answerId);
        }
        return JSON.toJSONString(new IsSuccessVO(likeEntity!=null,""));
    }
    @Override
    public String likeAnswer(String answerId) throws Exception {
        UserDTO user = UserHolder.getUser();
        if(user==null){
            //没登陆
            return JSON.toJSONString(new IsSuccessVO(false,"请登录"));
        }
        LikeEntity likeEntity = likeDao.selectLike(user.getId(),answerId);
        if(likeEntity==null){
            //没有喜欢
            likeDao.addLike(user.getId(),answerId);
            answerDao.updateLikes(answerId,1);
            return JSON.toJSONString(new IsSuccessVO(true,"1"));
        }else{
            //取消喜欢
            likeDao.deleteLike(user.getId(),answerId);
            answerDao.updateLikes(answerId,-1);
            return JSON.toJSONString(new IsSuccessVO(true,"-1"));
        }
    }
}
