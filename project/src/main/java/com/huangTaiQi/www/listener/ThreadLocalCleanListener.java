package com.huangTaiQi.www.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import java.lang.ref.Reference;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author _qqiu
 */
@WebListener
public class ThreadLocalCleanListener implements ServletRequestListener {
    private void cleanUpThreadLocals() throws Exception {
        Thread thread = Thread.currentThread();
        Field threadLocalsField = Thread.class.getDeclaredField("threadLocals");
        threadLocalsField.setAccessible(true);
        //获取当前线程的threadLocals字段的值。
        Object threadLocalsInThread = threadLocalsField.get(thread);
        //使用反射获取ThreadLocalMap类。
        Class<?> threadLocalMapClass = Class
                .forName("java.lang.ThreadLocal$ThreadLocalMap");
        Method removeInThreadLocalMap = threadLocalMapClass.getDeclaredMethod(
                "remove", ThreadLocal.class);
        removeInThreadLocalMap.setAccessible(true);
        //ThreadLocalMap是一个哈希表，它的底层实现是一个Entry数组，每个Entry包含一个ThreadLocal实例以及该线程上保存的变量。
        //ThreadLocalMap的table字段就是这个Entry数组。
        Field tableField = threadLocalMapClass.getDeclaredField("table");
        tableField.setAccessible(true);
        Object table = tableField.get(threadLocalsInThread);
        for (int i = 0; i < Array.getLength(table); i++) {
            Object entry = Array.get(table, i);
            Method getMethod = Reference.class.getDeclaredMethod("get");
            if (entry != null) {
                //获取到具体的ThreadLocal
                ThreadLocal<?> threadLocal = (ThreadLocal<?>) getMethod.invoke(entry);
                removeInThreadLocalMap.invoke(threadLocalsInThread, threadLocal);
            }
        }
    }
    @Override
    public void requestDestroyed(ServletRequestEvent paramServletRequestEvent) {
        try {
            cleanUpThreadLocals();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void requestInitialized(ServletRequestEvent paramServletRequestEvent) {
    }
}

