package com.my_framework.www.aop;

public interface AopProxy {

    Object getProxy();

    Object getProxy(ClassLoader classLoader);
}
