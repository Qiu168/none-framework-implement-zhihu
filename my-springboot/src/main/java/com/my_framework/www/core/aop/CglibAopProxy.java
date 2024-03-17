package com.my_framework.www.core.aop;


import com.my_framework.www.valid.annotation.Nullable;
import com.my_framework.www.core.aop.support.AdvisedSupport;
import net.sf.cglib.proxy.*;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;


/**
 * @author _qiu
 */
public class CglibAopProxy implements AopProxy {
    private final AdvisedSupport advised;

    public CglibAopProxy(AdvisedSupport config) {
        this.advised=config;
    }

    @Override
    public Object getProxy() {
        return getProxy(advised.getTargetClass().getClassLoader());
    }

    @Override
    public Object getProxy(@Nullable ClassLoader classLoader) {
        Enhancer enhancer=new Enhancer();
        if (classLoader != null) {
            enhancer.setClassLoader(classLoader);
        }
        Class<?> targetClass = advised.getTargetClass();
        enhancer.setSuperclass(targetClass);
        Callback[] callbacks = getCallbacks(advised);
        enhancer.setCallbacks(callbacks);
        enhancer.setCallbackFilter(new ProxyCallbackFilter(advised));
        return targetClass.cast(enhancer.create());
    }

    private Callback[] getCallbacks(AdvisedSupport advised) {
        Callback aopInterceptor = new DynamicAdvisedInterceptor(this.advised);
        Callback[] callbacks=new Callback[]{aopInterceptor,NoOp.INSTANCE};
        return callbacks;
    }
    private static class ProxyCallbackFilter implements CallbackFilter {

        private final AdvisedSupport advised;

        public ProxyCallbackFilter(AdvisedSupport advised) {
            this.advised = advised;
        }

        @Override
        public int accept(Method method) {
            //todo
            return 0;
        }
    }
    private static class DynamicAdvisedInterceptor implements MethodInterceptor, Serializable {

        private final AdvisedSupport advised;

        public DynamicAdvisedInterceptor(AdvisedSupport advised) {
            this.advised = advised;
        }

        @Override
        @Nullable
        public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            Class<?> targetClass = advised.getTargetClass();
            List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
            MethodInvocation invocation = new MethodInvocation(
                    proxy,
                    this.advised.getTarget(),
                    method,
                    args,
                    this.advised.getTargetClass(),
                    chain
            );
            //开始拦截器链的调用
            return invocation.proceed();
        }
    }
}
