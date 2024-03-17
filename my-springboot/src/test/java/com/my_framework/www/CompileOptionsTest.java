package com.my_framework.www;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

public class CompileOptionsTest {
    @Test
    public void testCompileOptions() throws NoSuchMethodException {
        Method method = CompileOptionsTest.class.getDeclaredMethod("methodWithParameters",String.class,int.class);
        Parameter[] parameters = method.getParameters();
        Assert.assertEquals("abc",parameters[0].getName());
        Assert.assertEquals("cba",parameters[1].getName());

    }

    // Example methods to test
    private void methodWithParameters(String abc, int cba) {
        // Method body
    }


}
