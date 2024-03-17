package com.my_framework.www;

import com.my_framework.www.core.context.Impl.DefaultResourceLoader;
import com.my_framework.www.core.context.ResourceName;
import org.junit.Assert;
import org.junit.Test;

public class ResourceTest {

    @Test
    public void testYaml(){
        DefaultResourceLoader resourceLoader=new DefaultResourceLoader();
        String value = resourceLoader.getValue("server.port");
        Assert.assertEquals("79779",value);
        String cc = resourceLoader.getValue("aaa.bbb");

        Assert.assertEquals("cc",cc);
        String password = resourceLoader.getValue("jdbc.password");
        Assert.assertEquals("abc",password);
    }
}
