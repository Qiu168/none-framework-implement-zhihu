package com.my_framework.www.db.orm;

import com.my_framework.www.db.pool.DataBaseUtil;

import java.lang.reflect.Proxy;

/**
 * @author _qiu
 */
public class MapperFactory {
    @SuppressWarnings("unchecked")
    public <T> T getMapper(Class<T> clazz) {
        MapperProxy mapperProxy = new MapperProxy(new BaseDao(DataBaseUtil.getConnection()));
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),new Class<?>[]{clazz}, mapperProxy);
    }

}
