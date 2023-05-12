package com.huangTaiQi.www.service.impl;

import com.alibaba.fastjson.JSON;
import com.huangTaiQi.www.dao.impl.FollowDao;
import com.huangTaiQi.www.dao.impl.UserDao;
import com.huangTaiQi.www.model.dto.UserDTO;
import com.huangTaiQi.www.model.entity.FollowEntity;
import com.huangTaiQi.www.model.entity.UserEntity;
import com.huangTaiQi.www.model.vo.IsSuccessVO;
import com.huangTaiQi.www.service.FollowService;
import com.huangTaiQi.www.utils.BeanUtil;
import com.huangTaiQi.www.utils.UserHolder;
import com.my_framework.www.annotation.Autowired;
import com.my_framework.www.annotation.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 14629
 */
@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    FollowDao followDao;
    @Autowired
    UserDao userDao;
    @Override
    public String isFollowed(String userId) throws Exception {
        UserDTO user = UserHolder.getUser();
        FollowEntity followEntity = null;
        if(user!=null){
            //没登陆
            followEntity=followDao.selectFollow(userId,user.getId());
        }
        return JSON.toJSONString(new IsSuccessVO(followEntity!=null,""));
    }
    @Override
    public String follow(Long userId) throws Exception {
        UserDTO user = UserHolder.getUser();
        if(user==null){
            //没登陆
            return JSON.toJSONString(new IsSuccessVO(false,"请登录"));
        }
        Long followeeId = user.getId();
        if(userId.equals(followeeId)){
            return JSON.toJSONString(new IsSuccessVO(false,"不可以关注自己"));
        }

        //TODO:查询拉黑
        FollowEntity followEntity=followDao.selectFollow(String.valueOf(userId),followeeId);
        if(followEntity==null){
            followDao.add(userId,followeeId);
            userDao.updateFollowee(userId,1);
        }else{
            followDao.delete(userId,followeeId);
            userDao.updateFollowee(userId,-1);
        }
        return JSON.toJSONString(new IsSuccessVO(true,followEntity==null?"关注成功":"取消关注"));
    }

    /**
     * 可以直接自连接出结果但是没必要
     * @param userId 用户id
     * @param id 自己
     * @return json
     */
    @Override
    public String getSameFollow(Long userId, Long id) throws Exception {
        if(userId.equals(id)){
            return JSON.toJSONString(null);
        }
        List<Long> follows1 = followDao.selectFollows(id);
        List<Long> follows2 = followDao.selectFollows(userId);
        if(follows2==null||follows1==null){
            return JSON.toJSONString(null);
        }
        //取交集，转换成hashmap在contain的时候效率更高
        Set<Long> set = new HashSet<>(follows2);
        List<Long> union = follows1.stream()
                .filter(set::contains)
                .collect(Collectors.toList());
        if(union.isEmpty()){
            return JSON.toJSONString(null);
        }
        List<UserEntity> users=new ArrayList<>();
        for (Long aLong : union) {
            //查询用户
            users.add(userDao.getUserById(String.valueOf(aLong)));
        }
        //返回UserDTO
        return JSON.toJSONString(
                users.stream()
                .map(user -> BeanUtil.copyProperties(user, UserDTO.class))
                .collect(Collectors.toList()));
    }
}
