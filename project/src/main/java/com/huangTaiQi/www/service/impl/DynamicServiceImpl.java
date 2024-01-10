package com.huangTaiQi.www.service.impl;

import com.alibaba.fastjson.JSON;
import com.huangTaiQi.www.constant.enums.MessageType;
import com.huangTaiQi.www.dao.impl.AnswerDao;
import com.huangTaiQi.www.dao.impl.FollowDao;
import com.huangTaiQi.www.dao.impl.QuestionDao;
import com.huangTaiQi.www.model.entity.AnswerEntity;
import com.huangTaiQi.www.model.entity.QuestionEntity;
import com.huangTaiQi.www.model.vo.DynamicResult;
import com.huangTaiQi.www.service.DynamicService;
import com.huangTaiQi.www.utils.UserHolder;
import com.my_framework.www.core.annotation.bean.Autowired;
import com.my_framework.www.core.annotation.stereotype.Service;
import com.my_framework.www.redis.JedisUtils;
import com.my_framework.www.utils.CastUtil;
import com.my_framework.www.utils.CollectionUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.resps.Tuple;

import java.util.ArrayList;
import java.util.List;

import static com.huangTaiQi.www.constant.JedisConstants.FEED_ANSWER_KEY;
import static com.huangTaiQi.www.constant.JedisConstants.FEED_QUESTION_KEY;

/**
 * 动态
 * @author 14629
 */
@Service
public class DynamicServiceImpl implements DynamicService {

    @Autowired
    FollowDao followDao;
    @Autowired
    QuestionDao questionDao;
    @Autowired
    AnswerDao answerDao;
    @Override
    public void sendDynamic(MessageType type, Long followerId, String questionId) throws Exception {
        //getFollowee
        List<Long> follows = followDao.selectFollowee(followerId);
        if(CollectionUtil.isEmpty(follows)){
            return;
        }
        if(type==MessageType.QUESTION){
            for (Long followeeId : follows) {
                Jedis jedis = JedisUtils.getJedis();
                String key=FEED_QUESTION_KEY+followeeId;
                jedis.zadd(key,System.currentTimeMillis(),questionId);
            }
        }else if (type==MessageType.ANSWER){
            for (Long followeeId : follows) {
                Jedis jedis = JedisUtils.getJedis();
                String key=FEED_ANSWER_KEY+followeeId;
                jedis.zadd(key,System.currentTimeMillis(),questionId);
            }
        }

    }
    @Override
    public String getDynamic(MessageType type,Long max, Integer offset, Integer pageSize) throws Exception {
        Long userId= UserHolder.getUser().getId();
        String key;
        if(type==MessageType.QUESTION){
            key=FEED_QUESTION_KEY+userId;
        }else {
            key=FEED_ANSWER_KEY+userId;
        }
        Jedis jedis = JedisUtils.getJedis();
        List<Tuple> tuples = jedis.zrevrangeByScoreWithScores(key, max, 0, offset, pageSize);
        if(CollectionUtil.isEmpty(tuples)){
            return JSON.toJSONString(null);
        }
        List<Long> ids=new ArrayList<>(tuples.size());
        long minTime=0L;
        int os=1;
        for (Tuple tuple : tuples) {
            ids.add(CastUtil.castLong(tuple.getElement()));
            //先转换成无小数的字符串
            String scoreStr = String.format("%.0f", tuple.getScore());
            long time= CastUtil.castLong(scoreStr);
            if(time==minTime){
                os++;
            }else{
                minTime=time;
                os=1;
            }
        }
        jedis.close();
        //查询,返回，minTime和os
        if(type==MessageType.QUESTION){
            List<QuestionEntity> questionByIds = questionDao.getQuestionByIds(ids);
            DynamicResult dynamicResult = new DynamicResult(questionByIds, minTime, os);
            return JSON.toJSONString(dynamicResult);
        }else {
            List<AnswerEntity> answerByIds = answerDao.getAnswerByIds(ids);
            return JSON.toJSONString(new DynamicResult(answerByIds,minTime,os));
        }
    }
    @Override
    public Long getDynamicCount(MessageType type){
        Long userId= UserHolder.getUser().getId();
        Jedis jedis = JedisUtils.getJedis();
        if(type==MessageType.QUESTION){
            return jedis.zcard(FEED_QUESTION_KEY+userId);
        }else{
            return jedis.zcard(FEED_ANSWER_KEY+userId);
        }
    }
}
