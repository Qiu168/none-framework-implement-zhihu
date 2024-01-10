package com.my_framework.www;


import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKProxyTest {
    interface targetI {
        void print();
    }

    class target implements targetI {
        int a = 1;

        public void print() {
            System.out.println(a);
        }
    }

    class handler implements InvocationHandler {
        target target = new target();

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("before");
            method.invoke(target, args);
            System.out.println("after");
            return proxy;
        }
    }

    @Test
    public void test() {
        targetI proxyInstance = (targetI) Proxy.newProxyInstance(target.class.getClassLoader(), target.class.getInterfaces(), new handler());
        proxyInstance.print();
        System.out.println(proxyInstance.getClass().getFields().length);
        System.out.println(proxyInstance.getClass().getSuperclass().getFields().length);
    }
}
