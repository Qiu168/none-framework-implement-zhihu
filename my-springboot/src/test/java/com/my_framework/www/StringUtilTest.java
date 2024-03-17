package com.my_framework.www;

import com.my_framework.www.utils.StringUtil;
import org.junit.Assert;
import org.junit.Test;

public class StringUtilTest {
    @Test
    public void testGetBeanName(){
        String controller1 = StringUtil.getBeanName("controller1");
        Assert.assertEquals("controller1",controller1);
    }
}
