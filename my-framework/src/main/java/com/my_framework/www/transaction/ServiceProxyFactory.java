package com.my_framework.www.transaction;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

/**
 * @author 14629
 */
public class ServiceProxyFactory {
    public static Object getProxy(Class<?> cls) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(cls);
        enhancer.setCallbacks(new Callback[]{new TransactionHandler(),NoOp.INSTANCE});
        enhancer.setCallbackFilter(new TransactionFilter());
        return cls.cast(enhancer.create());
    }
}