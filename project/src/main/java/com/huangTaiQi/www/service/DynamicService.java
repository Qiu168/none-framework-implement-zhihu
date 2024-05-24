package com.huangTaiQi.www.service;

import com.huangTaiQi.www.constant.enums.MessageType;

/**
 * @author _qqiu
 */
public interface DynamicService {
    /**
     * 发送动态
     * @param type 类型
     * @param followerId 博主id
     * @param questionId 问题id
     * @throws Exception 异常
     */
    void sendDynamic(MessageType type, Long followerId, String questionId) throws Exception;

    /**
     * 获取动态
     * @param type 类型
     * @param max 最大时间
     * @param offset offset
     * @param pageSize 大小
     * @return 动态json
     * @throws Exception 异常
     */
    String getDynamic(MessageType type, Long max, Integer offset, Integer pageSize) throws Exception;

    /**
     * 获取动态数量
     * @param type 类型
     * @return 数量
     */
    Long getDynamicCount(MessageType type);
}
