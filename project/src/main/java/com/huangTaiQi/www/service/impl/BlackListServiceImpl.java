package com.huangTaiQi.www.service.impl;

import com.alibaba.fastjson.JSON;
import com.huangTaiQi.www.dao.impl.BlackListDao;
import com.huangTaiQi.www.model.entity.BlackListEntity;
import com.huangTaiQi.www.service.BlackListService;
import com.huangTaiQi.www.utils.UserHolder;
import com.my_framework.www.annotation.Autowired;
import com.my_framework.www.annotation.Service;

import java.sql.SQLException;

/**
 * @author 14629
 */
@Service
public class BlackListServiceImpl implements BlackListService {
    @Autowired
    BlackListDao blackListDao;
    @Override
    public String getBlackListByUid() throws Exception {
        Long userId = UserHolder.getUser().getId();
        return JSON.toJSONString(blackListDao.getBlackListByUid(userId));
    }
    @Override
    public void addBlackList(String blackUid) throws SQLException {
        Long userId = UserHolder.getUser().getId();
        blackListDao.addBlackList(userId,blackUid);
        //TODO:取关
    }
    @Override
    public void deleteBlackList(String blackUid) throws SQLException{
        Long userId = UserHolder.getUser().getId();
        blackListDao.deleteBlackList(userId,blackUid);
    }

    public String isBlackList(String bid) throws Exception {
        Long userId = UserHolder.getUser().getId();
        BlackListEntity blackListEntity = blackListDao.selectBlackList(userId, bid);
        return JSON.toJSONString(blackListEntity);
    }
}
