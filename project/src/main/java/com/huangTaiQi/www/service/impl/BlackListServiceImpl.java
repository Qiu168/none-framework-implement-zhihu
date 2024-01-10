package com.huangTaiQi.www.service.impl;

import com.alibaba.fastjson.JSON;
import com.huangTaiQi.www.dao.impl.BlackListDao;
import com.huangTaiQi.www.dao.impl.FollowDao;
import com.huangTaiQi.www.model.dto.UserDTO;
import com.huangTaiQi.www.model.entity.BlackListEntity;
import com.huangTaiQi.www.model.entity.FollowEntity;
import com.huangTaiQi.www.model.vo.IsSuccessVO;
import com.huangTaiQi.www.service.BlackListService;
import com.huangTaiQi.www.utils.UserHolder;
import com.my_framework.www.core.annotation.bean.Autowired;
import com.my_framework.www.core.annotation.stereotype.Service;
import com.my_framework.www.utils.CastUtil;

import java.sql.SQLException;

/**
 * @author 14629
 */
@Service
public class BlackListServiceImpl implements BlackListService {
    @Autowired
    BlackListDao blackListDao;
    @Autowired
    FollowDao followDao;
    @Override
    public String getBlackListByUid() throws Exception {
        Long userId = UserHolder.getUser().getId();
        return JSON.toJSONString(blackListDao.getBlackListByUid(userId));
    }
    @Override
    public IsSuccessVO addBlackList(String blackUid) throws Exception {
        UserDTO user = UserHolder.getUser();
        if(user==null){
            return new IsSuccessVO(false,"请登录");
        }
        Long userId = user.getId();
        if(CastUtil.castLong(blackUid) == userId){
            return new IsSuccessVO(false,"不能拉黑自己");
        }

        blackListDao.addBlackList(userId,blackUid);
        //取关
        FollowEntity followEntity = followDao.selectFollow(blackUid, userId);
        if(followEntity!=null){
            followDao.delete(CastUtil.castLong(blackUid),userId);
        }
        return new IsSuccessVO(true,"success");
    }
    @Override
    public void deleteBlackList(String blackUid) throws SQLException{
        Long userId = UserHolder.getUser().getId();
        blackListDao.deleteBlackList(userId,blackUid);
    }
    @Override
    public BlackListEntity selectBlackList(String bid) throws Exception {
        Long userId = UserHolder.getUser().getId();
        return blackListDao.selectBlackList(userId, bid);
    }
}
