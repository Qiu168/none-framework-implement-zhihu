package com.my_framework.www.core.aop;

import com.my_framework.www.core.aop.support.AdvisedSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * JDK 动态 AOP 代理
 *
 * @author _qqiu
 * @date
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    private AdvisedSupport advised;

    public JdkDynamicAopProxy(AdvisedSupport config) {
        this.advised = config;
    }

    @Override
    public Object getProxy() {
        return getProxy(this.advised.getTargetClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return Proxy.newProxyInstance(classLoader, this.advised.getTargetClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //获取拦截器链
        List<Object> interceptorsAndDynamicMethodMatchers =
                this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, this.advised.getTargetClass());
        //外层拦截器，用于控制拦截器链的执行
        MethodInvocation invocation = new MethodInvocation(
                proxy,
                this.advised.getTarget(),
                method,
                args,
                this.advised.getTargetClass(),
                interceptorsAndDynamicMethodMatchers
        );
        //开始拦截器链的调用
        return invocation.proceed();
    }
}
