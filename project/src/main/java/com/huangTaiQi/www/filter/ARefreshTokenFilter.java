package com.huangTaiQi.www.filter;

import com.huangTaiQi.www.model.dto.UserDTO;
import com.huangTaiQi.www.utils.BeanUtil;
import com.huangTaiQi.www.utils.JedisPoolFactory;
import com.huangTaiQi.www.utils.StringUtils;
import com.huangTaiQi.www.utils.UserHolder;
import redis.clients.jedis.Jedis;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static com.huangTaiQi.www.constant.JedisConstants.LOGIN_USER_KEY;
import static com.huangTaiQi.www.constant.JedisConstants.LOGIN_USER_TTL;

/**
 * @author 14629
 */
public class ARefreshTokenFilter extends BaseFilter{
    @Override
    protected boolean doBeforeProcessing(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws Exception {
        Jedis jedis = JedisPoolFactory.getJedis();
        //获取token
        String token = request.getHeader("authorization");
        if(StringUtils.isEmpty(token)){
            //不存在
            return true;
        }
        token=StringUtils.getToken(token);
        String key = LOGIN_USER_KEY + token;
        Map<String, String> userMap = jedis.hgetAll(key);
        if(userMap.isEmpty()){
            return true;
        }
        // 将查询到的hash数据转为UserDTO
        UserDTO user = new UserDTO();
        BeanUtil.fillBeanWithMap(user,userMap);
        // 存在，保存用户信息到 ThreadLocal
        UserHolder.saveUser(user);
        // 刷新token有效期
        jedis.expire(key, LOGIN_USER_TTL);
        return true;
    }
}
