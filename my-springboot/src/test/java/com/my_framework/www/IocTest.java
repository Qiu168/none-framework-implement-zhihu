package com.my_framework.www;

import com.my_framework.www.core.context.ApplicationContext;
import com.my_framework.www.core.context.Impl.GenericApplicationContext;
import com.my_framework.www.exception.DynamicBeanNotKnowWhichAutowireException;
import com.my_framework.www.test.Controller1;
import com.my_framework.www.test.Service1;
import com.my_framework.www.test.Service2;
import org.junit.Assert;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import java.util.Arrays;

public class IocTest {
    ApplicationContext context=new GenericApplicationContext("com.my_framework.www.test");
    @Test
    public void testBase() throws Exception {
        System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
        Object controller1 = context.getBean("controller1");
        Object controller2 = context.getBean("com.my_framework.www.test.Controller1");
        Object dao = context.getBean("com.my_framework.www.test.Dao1");
        Service1 bean = context.getBean(Service1.class);
        //验证@value
        Assert.assertEquals("abc",bean.abc);
        //验证接口注入
        Assert.assertNotNull(bean.dao1);
        Assert.assertEquals(dao,bean.dao1);
        Assert.assertNotNull(controller2);
        Assert.assertNotNull(controller1);
    }
    @Test
    public void testDynamic() throws Exception {
        Controller1 controller1 = (Controller1)context.getBean("controller1");
        Assert.assertEquals(Service2.class,controller1.service.getClass());
    }
}
