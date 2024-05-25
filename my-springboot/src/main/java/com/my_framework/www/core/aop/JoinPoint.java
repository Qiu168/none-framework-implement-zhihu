package com.my_framework.www.core.aop;

import java.lang.reflect.Method;

/**
 * 连接点
 * @author 14629
 */
public interface JoinPoint {
    Object getTarget();

    Object[] getArguments();

    Method getMethod();
}
