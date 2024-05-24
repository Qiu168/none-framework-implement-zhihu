package com.my_framework.www.db.transaction;

import com.my_framework.www.db.pool.DataBaseUtil;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 问题：有必要redis事务吗？
 * @author _qqiu
 */
public class TransactionHandler implements MethodInterceptor {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Object result = null;
        try {
            // 开启事务
            DataBaseUtil.beginTransaction();
            //JedisUtils.beginTransaction();
            result = proxy.invokeSuper(obj, args);
            // 提交事务
            DataBaseUtil.commitTransaction();
            //JedisUtils.commitTransaction();
        } catch (Exception e) {
            // 回滚事务
            DataBaseUtil.rollbackTransaction();
            //JedisUtils.rollbackTransaction();
            e.printStackTrace();
        } finally {
            // 关闭事务
            DataBaseUtil.close();
            //JedisUtils.close();
        }
        return result;
    }

}