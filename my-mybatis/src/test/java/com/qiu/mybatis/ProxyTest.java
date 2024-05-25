package com.qiu.mybatis;

import com.qiu.mybatis.proxy.MapperHandler;
import org.junit.Test;

import java.lang.reflect.Proxy;
import java.util.Arrays;

public class ProxyTest {
    @Test
    public void testJdkProxy() throws InstantiationException, IllegalAccessException {
        UserMapper instance =(UserMapper) Proxy.newProxyInstance(this.getClass().getClassLoader(),new Class[]{UserMapper.class}, new MapperHandler());
        System.out.println(instance);
        System.out.println(instance.getClass());
        UserMapper userMapper = instance.getClass().newInstance();
        System.out.println(userMapper);
    }
}
