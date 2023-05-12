package com.huangTaiQi.www.service.impl;

import com.alibaba.fastjson.JSON;
import com.huangTaiQi.www.service.RightService;
import com.huangTaiQi.www.utils.UserHolder;
import com.my_framework.www.annotation.Service;
import com.my_framework.www.redis.JedisUtils;
import com.my_framework.www.utils.CastUtil;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Map;

import static com.huangTaiQi.www.constant.JedisConstants.BAN_USER_RIGHT;

/**
 * @author 14629
 */
@Service
public class RightServiceImpl implements RightService {
    @Override
    public void banUserRight(Integer right, Long banTime){
        Jedis jedis = JedisUtils.getJedis();
        String key=BAN_USER_RIGHT+UserHolder.getUser().getId();
        jedis.hset(key, String.valueOf(right), String.valueOf(0));
        jedis.expire(key + ":" + right, banTime);
    }
    @Override
    public String getRight() {
        Map<Long, Boolean> userRightMap = UserHolder.getUserRightMap();
        //获取被封禁的权限
        Jedis jedis = JedisUtils.getJedis();
        String key=BAN_USER_RIGHT+UserHolder.getUser().getId();
        Map<String, String> userBanRightMap = jedis.hgetAll(key);
        for (Map.Entry<String, String> banRightEntry : userBanRightMap.entrySet()) {
            //遍历被禁止的权限，并查询用户是否存在被禁止的权限
            Boolean right = userRightMap.getOrDefault(CastUtil.castLong(banRightEntry.getKey()), false);
            //如果存在被禁止权限,从map移除
            if(right){
                userRightMap.remove(CastUtil.castLong(banRightEntry.getKey()));
            }
        }
        String json;
        if(userRightMap==null){
            json= JSON.toJSONString(null);
        }else{
            json = JSON.toJSONString(new ArrayList<>(userRightMap.keySet()));
        }
        jedis.close();
        return json;
    }
}
