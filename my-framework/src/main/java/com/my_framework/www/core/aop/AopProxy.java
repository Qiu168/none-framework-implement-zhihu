package com.my_framework.www.core.aop;

public interface AopProxy {

    Object getProxy();

    Object getProxy(ClassLoader classLoader);
}
