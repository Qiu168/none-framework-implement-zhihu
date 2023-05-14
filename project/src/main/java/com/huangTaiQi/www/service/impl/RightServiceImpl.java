package com.huangTaiQi.www.service.impl;

import com.alibaba.fastjson.JSON;
import com.huangTaiQi.www.dao.impl.RightDao;
import com.huangTaiQi.www.dao.impl.UserDao;
import com.huangTaiQi.www.model.entity.RoleRightRelationEntity;
import com.huangTaiQi.www.model.entity.UserEntity;
import com.huangTaiQi.www.service.RightService;
import com.huangTaiQi.www.utils.UserHolder;
import com.my_framework.www.annotation.Autowired;
import com.my_framework.www.annotation.Service;
import com.my_framework.www.redis.JedisUtils;
import com.my_framework.www.utils.CastUtil;
import com.my_framework.www.utils.CollectionUtil;
import com.my_framework.www.utils.StringUtil;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.huangTaiQi.www.constant.JedisConstants.BAN_USER_RIGHT;
import static com.huangTaiQi.www.constant.RightConstants.RIGHTS;
import static com.huangTaiQi.www.constant.UserRoleConstants.VISITOR;

/**
 * @author 14629
 */
@Service
public class RightServiceImpl implements RightService {
    @Autowired
    UserDao userDao;
    @Autowired
    RightDao rightDao;
    @Override
    public void banUserRight(String username, Integer right, Long banTime) throws Exception {
        UserEntity userByUsername = userDao.getUserByUsername(username);
        Jedis jedis = JedisUtils.getJedis();
        jedis.setex(BAN_USER_RIGHT+userByUsername.getId()+":"+right,banTime*60,"0");
    }
    @Override
    public String getRight() {
        Map<Long, Boolean> userRightMap = UserHolder.getUserRightMap();
//        List<Integer> userRight=new ArrayList<>();
//        //获取被封禁的权限
//        Jedis jedis = JedisUtils.getJedis();
//        getRightMapAfterBan(userRightMap, jedis,UserHolder.getUser().getId().toString());
//        String json;
//        if(userRightMap==null){
//            json= JSON.toJSONString(null);
//        }else{
//            json = JSON.toJSONString(new ArrayList<>(userRightMap.keySet()));
//        }
//        jedis.close();
        return JSON.toJSONString(new ArrayList<>(userRightMap.keySet()));
    }

    public static void getRightMapAfterBan(Map<Long, Boolean> userRightMap, Jedis jedis,String userId) {
        List<Integer> banRightList=new ArrayList<>();
        for (int right : RIGHTS) {
            //遍历权限
            String key=BAN_USER_RIGHT+userId+":"+right;
            String s = jedis.get(key);
            if(StringUtil.isNotEmpty(s)){
                banRightList.add(right);
            }
        }
        for (Integer banRight :banRightList) {
            Long banRightL=CastUtil.castLong(banRight);
            //遍历被禁止的权限，并查询用户是否存在被禁止的权限
            Boolean right = userRightMap.getOrDefault(banRightL, false);
            //如果存在被禁止权限,从map移除
            if(right){
                userRightMap.remove(banRightL);
            }
        }
    }
    @Override
    public String getRightByUsername(String username) throws Exception {
        UserEntity userByUsername = userDao.getUserByUsername(username);
        if(userByUsername!=null){
            ArrayList<Long> rightByUserId = getRightByUserId(userByUsername.getRoleId(), userByUsername.getId());
            if(CollectionUtil.isEmpty(rightByUserId)){
                return JSON.toJSONString(null);
            }else{
                return JSON.toJSONString(rightByUserId);
            }
        }else{
            return JSON.toJSONString(null);
        }

    }

    private ArrayList<Long> getRightByUserId(Long roleId, Long uid) throws Exception {
        List<RoleRightRelationEntity> userRight = rightDao.getUserRight(roleId);
        Map<Long, Boolean> rightMap = userRight.stream().collect(Collectors.toMap(RoleRightRelationEntity::getRightId, right -> true));
        Jedis jedis = JedisUtils.getJedis();
        getRightMapAfterBan(rightMap, jedis, uid.toString());
        return new ArrayList<>(rightMap.keySet());
    }
    @Override
    public void banUserByUsername(String username) throws Exception {
        UserEntity userByUsername = userDao.getUserByUsername(username);
        userDao.updateRole(userByUsername.getId(),VISITOR);
    }
}
