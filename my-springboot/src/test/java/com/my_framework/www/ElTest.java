package com.my_framework.www;

import com.my_framework.www.core.context.Impl.DefaultResourceLoader;
import com.my_framework.www.core.context.ResourceLoader;
import com.my_framework.www.core.el.ElInterpreter;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ElTest {

    @Test
    public void testMatchEl(){
        String s="dajbsk${123456a}dahsj${dhasjkda}kd";
        //String[] contentInfo = getContentInfo(s);
        ResourceLoader resourceLoader=new DefaultResourceLoader();
        String parsing = ElInterpreter.parsing(resourceLoader,s);
        Assert.assertEquals("dajbsk123dahsjabckd",parsing);
        //Assert.assertEquals(new String[]{"${123456}","${dhasjkda}"},contentInfo);
    }
    public static String[] getContentInfo(String content) {
        Pattern regex = Pattern.compile("\\$\\{([^}]*)}");
        Matcher matcher = regex.matcher(content);
        ArrayList<String> sql = new ArrayList<>();
        while(matcher.find()) {
            sql.add(matcher.group()/*matcher.group(1)*/);
        }
        return sql.toArray(new String[0]);
    }

}
