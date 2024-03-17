package com.my_framework.www.webmvc;

/**
 * @author _qiu
 */
public interface Interceptor {
    /**
     * 是否拦截该方法
     * @return 拦截返回true
     */
    boolean doHandle();

    /**
     * 执行方法前的操作
     * @return 返回false后面不执行了。
     * @throws Exception 统一异常管理
     */
    boolean preHandle() throws Exception;

    /**
     * 执行方法后的操作
     * @throws Exception todo
     */
    void postHandle() throws Exception;

}
