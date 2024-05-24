package com.my_framework.www.core.aop;


import java.lang.reflect.Method;

/**
 * todo
 * @author _qqiu
 */
public class ProceedingJoinPoint implements JoinPoint{
    public Object proceed() throws Throwable {
        return null;
    }

    @Override
    public Object getTarget() {
        return null;
    }

    @Override
    public Object[] getArguments() {
        return new Object[0];
    }

    @Override
    public Method getMethod() {
        return null;
    }


}
