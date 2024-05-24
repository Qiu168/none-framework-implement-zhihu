package com.my_framework.www.db.transaction;

import com.my_framework.www.db.annotation.Transaction;
import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

/**
 * @author _qqiu
 */
public class TransactionFilter implements CallbackFilter {

    @Override
    public int accept(Method method) {
        if (method.isAnnotationPresent(Transaction.class)) {
            //如果此方法被Transaction注解，就代理
            return 0;
        }
        return 1;
    }
    
}