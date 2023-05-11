package com.huangTaiQi.www.controller;

import com.my_framework.www.annotation.RequestMapping;
import com.my_framework.www.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 14629
 */
public interface IDynamicController {
    /**
     * 获取动态问题
     * @param offset offset防止同一时间的多条信息
     * @param max 获取问题的最大时间
     * @param pageSize 大小
     * @param response resp
     * @throws Exception 异常
     */
    void getDynamicQuestion(Integer offset, Long max, Integer pageSize, HttpServletResponse response) throws Exception;

    /**
     * 获取动态回答
     * @param offset offset防止同一时间的多条信息
     * @param max 获取问题的最大时间
     * @param pageSize 大小
     * @param response resp
     * @throws Exception 异常
     */
    void getDynamicAnswer(Integer offset, Long max,  Integer pageSize, HttpServletResponse response) throws Exception;

    /**
     * 获取动态的总数
     * @param type 类型
     * @param response resp
     * @throws IOException 异常
     */
    void getDynamicTotal(String type, HttpServletResponse response) throws IOException;
}
