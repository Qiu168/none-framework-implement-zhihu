package com.huangTaiQi.www.service.impl;

import com.alibaba.fastjson.JSON;
import com.huangTaiQi.www.dao.impl.FollowDao;
import com.huangTaiQi.www.dao.impl.RightDao;
import com.huangTaiQi.www.dao.impl.UserDao;
import com.huangTaiQi.www.model.dto.UserDTO;
import com.huangTaiQi.www.model.entity.FollowEntity;
import com.huangTaiQi.www.model.entity.RoleRightRelationEntity;
import com.huangTaiQi.www.model.entity.UserEntity;
import com.huangTaiQi.www.service.UserService;
import com.huangTaiQi.www.utils.*;
import com.huangTaiQi.www.utils.code.Md5Utils;
import com.my_framework.www.annotation.Autowired;
import com.my_framework.www.annotation.Service;
import com.my_framework.www.annotation.Transaction;
import com.my_framework.www.redis.JedisUtils;
import redis.clients.jedis.Jedis;


import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.huangTaiQi.www.constant.ImgConstants.CODES;
import static com.huangTaiQi.www.constant.JedisConstants.*;
import static com.huangTaiQi.www.constant.MailConstants.MAIL_CONTENT;
import static com.huangTaiQi.www.constant.MailConstants.MAIL_TITLE;
import static com.huangTaiQi.www.constant.RegexConstants.*;
import static com.huangTaiQi.www.constant.ResponseConstants.*;
import static com.huangTaiQi.www.constant.SessionConstants.IMG_CODE;

/**
 * @author 14629
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    FollowDao followDao;
    @Autowired
    RightDao rightDao;

    Logger logger= Logger.getLogger(UserServiceImpl.class.getName());
    @Override
    public BufferedImage imgCode(HttpSession session) {
        //使用验证码类，生成验证码类对象
        ImgVerifyCode ivc = new ImgVerifyCode();
        //获取验证码
        BufferedImage image = ivc.getImage();
        String code = ivc.getText();
        logger.log(Level.INFO,"code:"+code);
        //存到session
        session.setAttribute(IMG_CODE,code);
        return image;
    }
    @Override
    public String sendEmail(String email,String imgCode,String code){
        //校验邮箱，不正确返回错误信息
        if (!RegexUtils.check(EMAIL_REGEX,email)) {
            // 如果不符合，返回错误信息
            return WRONG_EMAIL;
        }
        //校验图形验证码
        if(imgCode ==null||!imgCode.equalsIgnoreCase(code)){
            return WRONG_CODE;
        }
        // 使用线程安全的 SecureRandom 生成邮箱验证码
        SecureRandom random = new SecureRandom();
        StringBuilder emailCode = new StringBuilder();
        final int emailCodeLength=6;
        for (int i = emailCodeLength; i > 0; i--) {
            emailCode.append(CODES.charAt(random.nextInt(CODES.length())));
        }
        //发送邮件
        boolean sendMail = MailUtils.sendMail(email, MAIL_CONTENT + emailCode, MAIL_TITLE);
        //将验证码存入redis
        Jedis jedis = JedisUtils.getJedis();
        jedis.setex(LOGIN_CODE_KEY+email,LOGIN_CODE_TTL,emailCode.toString());
        //发送成功，返回信息
        if(sendMail){
            return SUCCESS_SEND;
        }
        //失败信息
        return FAIL_SEND;
    }

    @Override
    public boolean hasEmail(String email) throws Exception {
        UserEntity userEntity = userDao.selectByEmail(email);
        return userEntity!=null;
    }

    @Override
    @Transaction
    public String register(String email, String emailCode, String password, String rePassword) throws SQLException, NoSuchAlgorithmException {
        String user = verifyUser(email, emailCode, password, rePassword);
        if(user!=null){
            //验证失败，返回提示信息
            return user;
        }
        //验证成功，存入数据进数据库
        userDao.setEmailAndPassword(email, Md5Utils.encode(password));
        return SUCCESS_REGISTER;
    }

    @Override
    @Transaction
    public String login(String usernameOrEmail, String password, String imgCode, String code) throws Exception {
        UserEntity user;
        //校验图形验证码
        if(imgCode ==null||!imgCode.equalsIgnoreCase(code)){
            return WRONG_CODE;
        }
        if(RegexUtils.check(EMAIL_REGEX,usernameOrEmail)){
            //邮箱登录
            user = userDao.selectByEmailAndPassword(usernameOrEmail, Md5Utils.encode(password));
        }else {
            //用户名登录
            user=userDao.selectByUsernameAndPassword(usernameOrEmail, Md5Utils.encode(password));
        }
        // 判断用户是否存在
        if (user == null) {
            // 不存在,返回错误信息
            return WRONG_LOGIN;
        }
        // 随机生成token，作为登录令牌
        String token = UUID.randomUUID().toString();
        // 将User对象转为HashMap存储
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        Map<String, String> userMap = BeanUtil.beanToMap(userDTO);
        // 存储用户信息到threadLocal
        UserHolder.saveUser(userDTO);
        System.out.println(Thread.currentThread());
        // 存储 保存用户信息到 redis中
        String tokenKey = LOGIN_USER_KEY + token;
        Jedis jedis = JedisUtils.getJedis();
        jedis.hmset(tokenKey,userMap);
        // 设置token有效期
        jedis.expire(tokenKey, LOGIN_USER_TTL);
        // 获取用户权限
        List<RoleRightRelationEntity> userRight = rightDao.getUserRight(user.getRoleId());
        // 制作成权限表
        Map<Long, Boolean> rightMap;
        if(userRight!=null){
            rightMap = userRight.stream().collect(Collectors.toMap(RoleRightRelationEntity::getRightId, right -> true));
            // 查询并删除被封禁的权限
            RightServiceImpl.getRightMapAfterBan(rightMap, jedis,user.getId().toString());
        }else{
            rightMap=new HashMap<>();
        }
        UserHolder.saveUserRight(rightMap);
        // 存储到redis,存JSON
        String rightKey = USER_RIGHT_KEY + token;
        jedis.set(rightKey,JSON.toJSONString(rightMap));
        // 设置有效期
        jedis.expire(rightKey, USER_RIGHT_TTL);
        // 返回token
        return TOKEN+token;
    }

    @Override
    public String resetPassword(String email, String emailCode, String password, String rePassword) throws NoSuchAlgorithmException, SQLException {
        String user = verifyUser(email, emailCode, password, rePassword);
        if(user!=null) {
            //验证失败，返回提示信息
            return user;
        }
        //验证成功，重置密码
        userDao.alterPassword(email, Md5Utils.encode(password));
        return SUCCESS_REGISTER;
    }
    @Override
    public String verifyUser(String email, String emailCode, String password, String rePassword){
        //从redis中获取邮箱验证码
        Jedis jedis = JedisUtils.getJedis();
        String code = jedis.get(LOGIN_CODE_KEY + email);
        if(code==null || !code.equalsIgnoreCase(emailCode)){
            //验证码过期或错误,返回提示信息
            return WRONG_EMAIL_CODE;
        }
        if(password==null||!RegexUtils.check(PASSWORD_REGEX,password)|| !password.equals(rePassword)){
            //检查密码是否为空，正则判断长度，是否两个密码相同
            return WRONG_PASSWORD;
        }
        return null;
    }

    @Override
    public String getUser(String username) throws Exception {
        List<UserDTO> users = userDao.getUserByBlurUsername(username)
                .stream()
                .map(user -> BeanUtil.copyProperties(user, UserDTO.class))
                .collect(Collectors.toList());
        return JSON.toJSONString(users);
    }
    @Override
    public String getUserById(String id) throws Exception {
        UserDTO userDTO = BeanUtil.copyProperties(userDao.getUserById(id), UserDTO.class);
        return  JSON.toJSONString(userDTO);
    }
    @Override
    public void updateUserSettings(String username, String gender, String email, String introduce, String imgPath) throws SQLException {
        if(imgPath==null){
            //如果没有跟新图片
            imgPath= UserHolder.getUser().getAvatar();
        }
        userDao.updateUserSettings(username,gender,email,introduce,imgPath);
    }

    /**
     * 是否互关
     * @param user 当前用户
     * @param username 查询的用户昵称
     * @return 如果互关，返回查询的用户Id，否则返回0
     */
    @Override
    public Long isFollowEachOther(UserDTO user, String username) throws Exception {
        UserEntity userByUsername = userDao.getUserByUsername(username);
        if(userByUsername==null){
            return 0L;
        }
        FollowEntity followEntity=followDao.getEachFollow(user.getId(),userByUsername.getId());
        return followEntity==null?0:followEntity.getUserId();
    }

}
