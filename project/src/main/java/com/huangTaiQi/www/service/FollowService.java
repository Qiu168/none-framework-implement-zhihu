package com.huangTaiQi.www.service;

/**
 * @author 14629
 */
public interface FollowService {
    /**
     * 是否关注
     * @param userId 用户id
     * @return 是否关注
     * @throws Exception 异常
     */
    String isFollowed(String userId) throws Exception;

    /**
     * 关注
     * @param userId 博主id
     * @return 是否关注成功
     * @throws Exception 异常
     */
    String follow(Long userId) throws Exception;

    /**
     * 获取共同关注
     * @param userId uid
     * @param id 博主id
     * @return 共同关注
     * @throws Exception 异常
     */
    String getSameFollow(Long userId, Long id) throws Exception;
}
