package com.my_framework.www.core.aop.intercept;

import com.my_framework.www.core.aop.MethodInvocation;

public interface MethodInterceptor {
    Object invoke(MethodInvocation invocation) throws Throwable;
}
