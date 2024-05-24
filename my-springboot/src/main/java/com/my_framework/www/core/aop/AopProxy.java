package com.my_framework.www.core.aop;

/**
 * AOP 代理
 *
 * @author _qqiu
 */
public interface AopProxy {

    Object getProxy();

    Object getProxy(ClassLoader classLoader);
}
