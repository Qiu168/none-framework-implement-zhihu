package com.huangTaiQi.www.controller;

import javax.servlet.http.HttpServletResponse;

/**
 * @author _qqiu
 */
public interface ILikeController {
    /**
     * 是否喜欢
     * @param answerId 回答id
     * @param response resp
     * @throws Exception 异常
     */
    void isLike(String answerId, HttpServletResponse response) throws Exception;

    /**
     * 喜欢回答
     * @param answerId 回答id
     * @param response resp
     * @throws Exception 异常
     */
    void likeAnswer(String answerId, HttpServletResponse response) throws Exception;
}
