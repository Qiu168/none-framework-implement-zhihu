package com.my_framework.www.db.orm;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

/**
 * @author _qiu
 */
public class MapperProxy implements InvocationHandler {

    private final BaseDao baseDao;
    private static final MapperConfiguration CONFIGURATION =new XMLConfigBuilder().parse();

    public MapperProxy(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String statementKey = method.getDeclaringClass().getName()+"."+method.getName();
        MapperStatement mapperStatement = CONFIGURATION.getMapperStatement().get(statementKey);
        Class<?> clazz = method.getReturnType();
        // 如何返回类型是集合类型的子类
        if (Collection.class.isAssignableFrom(clazz)){
            // 表示查询多条数据
            baseDao.selectByParams(mapperStatement.getSql(),Class.forName( mapperStatement.getResultType()),args);
        }else if (Map.class.isAssignableFrom(clazz)){
            // 表示要返回Map集合
            //todo
        }else {
            // 返回对象数据
            return baseDao.selectOne(mapperStatement.getSql(),Class.forName( mapperStatement.getResultType()),args);
        }
        return null;
    }
}
