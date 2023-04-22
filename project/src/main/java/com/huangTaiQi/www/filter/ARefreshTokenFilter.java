package com.huangTaiQi.www.filter;

import com.huangTaiQi.www.model.dto.UserDTO;
import com.huangTaiQi.www.utils.*;
import com.my_framework.www.redis.JedisUtils;
import redis.clients.jedis.Jedis;

import javax.servlet.FilterChain;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Map;

import static com.huangTaiQi.www.constant.JedisConstants.LOGIN_USER_KEY;
import static com.huangTaiQi.www.constant.JedisConstants.LOGIN_USER_TTL;

/**
 * @author 14629
 */
@WebFilter("/*")
public class ARefreshTokenFilter extends BaseFilter{
    @Override
    protected boolean doBeforeProcessing(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws Exception {
        Jedis jedis = JedisUtils.getJedis();
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
        // 刷新token有效期 TODO:刷新权限有效期
        jedis.expire(key, LOGIN_USER_TTL);
        JedisUtils.close();
        return true;
    }

    @Override
    protected void doAfterProcessing(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws SQLException {
        //请求结束后移除ThreadLocal中的值，防止影响。（内存泄漏
        UserHolder.removeUser();
        JedisUtils.close();
    }
}
