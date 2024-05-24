package com.qiu.mybatis;

import com.qiu.mybatis.proxy.MapperHandler;
import org.junit.Test;

import java.lang.reflect.Proxy;
import java.util.Arrays;

public class ProxyTest {
    @Test
    public void testJdkProxy(){
        UserMapper instance =(UserMapper) Proxy.newProxyInstance(this.getClass().getClassLoader(),new Class[]{UserMapper.class}, new MapperHandler());
        instance.selectUserId("123");
        instance.insert(new User());
    }
}
