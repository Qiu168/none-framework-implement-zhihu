package com.huangTaiQi.www.service;

/**
 * @author _qqiu
 */
public interface LikeService {
    /**
     * 是否喜欢
     * @param answerId 回答id
     * @return 是否喜欢
     * @throws Exception 异常
     */
    String isLike(String answerId) throws Exception;

    /**
     * 给回答点赞
     * @param answerId 回答id
     * @return 是否成功
     * @throws Exception 异常
     */
    String likeAnswer(String answerId) throws Exception;
}
