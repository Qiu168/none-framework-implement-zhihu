package com.my_framework.www.webmvc;

import com.my_framework.www.valid.annotation.Nullable;

import javax.servlet.http.HttpServletRequest;

/**
 * @author _qiu
 */
public interface HandlerMethodArgumentResolver {
    /**
     * 该参数是否要进行操作
     * @param parameter 参数
     */
    boolean supportsParameter(Object parameter);

    /**
     * 改变参数
     * @param parameter 参数
     * @param webRequest request
     * @return 参数的新值
     * @throws Exception 异常
     */
    @Nullable
    Object resolveArgument(Object parameter, HttpServletRequest webRequest) throws Exception;
}
