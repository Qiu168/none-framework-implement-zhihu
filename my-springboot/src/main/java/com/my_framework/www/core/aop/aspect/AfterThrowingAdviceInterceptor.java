package com.my_framework.www.core.aop.aspect;



import com.my_framework.www.core.aop.intercept.MethodInterceptor;
import com.my_framework.www.core.aop.MethodInvocation;
import lombok.Setter;

import java.lang.reflect.Method;

/**
 * 异常通知
 *
 * @author _qqiu
 */
@Setter
public class AfterThrowingAdviceInterceptor extends AbstractAspectAdvice implements MethodInterceptor {

    private String throwName;

    public AfterThrowingAdviceInterceptor(Method aspectMethod, Object aspectTarget) {
        super(aspectMethod, aspectTarget);
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        try {
            //直接调用下一个拦截器，如果不出现异常就不调用异常通知
            return mi.proceed();
        } catch (Throwable e) {
            //异常捕捉中调用通知方法
            invokeAdviceMethod(mi, null, e.getCause());
            throw e;
        }
    }

}
