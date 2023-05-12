package com.huangTaiQi.www.controller;

import com.my_framework.www.annotation.RequestMapping;
import com.my_framework.www.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 14629
 */
public interface IFollowController {
    /**
     * 是否关注
     * @param userId 博主id
     * @param response resp
     * @throws Exception 异常
     */
    void isFollowed(String userId, HttpServletResponse response) throws Exception;

    /**
     * 关注
     * @param userId 博主id
     * @param response resp
     * @throws Exception 异常
     */
    void follow(Long userId, HttpServletResponse response) throws Exception;

    /**
     * 获取共同关注
     * @param userId 博主id
     * @param response resp
     * @throws Exception 异常
     */
    void getSameFollow( Long userId, HttpServletResponse response) throws Exception;
}
