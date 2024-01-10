package com.my_framework.www;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Pattern;

public class PattenTest {
    @Test
    public void test(){
        Assert.assertTrue(Pattern.matches("^[0-9]+$", "   789    ".trim()));
    }
}
