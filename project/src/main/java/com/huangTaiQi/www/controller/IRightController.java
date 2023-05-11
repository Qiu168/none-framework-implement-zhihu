package com.huangTaiQi.www.controller;

import com.my_framework.www.annotation.RequestMapping;
import com.my_framework.www.annotation.RequestParam;

/**
 * @author 14629
 */
public interface IRightController {
    /**
     * 禁用user的权限
     * @param right 权限
     * @param banTime 禁用时间
     */
    void banRight(Integer right,Long banTime);
}
