package com.my_framework.www.core.aop;

import java.lang.reflect.Method;

/**
 * 连接点
 */
public interface JoinPoint {
    Object getTarget();

    Object[] getArguments();

    Method getMethod();
}
