package com.my_framework.www.aop.intercept;

public interface MethodInterceptor {
    Object invoke(MethodInvocation invocation) throws Throwable;
}
