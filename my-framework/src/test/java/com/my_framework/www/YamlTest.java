package com.my_framework.www;

import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class YamlTest {
    @Test
    public void testYaml(){
        Yaml yaml=new Yaml();

        try (InputStream resourceAsStream = YamlTest.class.getClassLoader().getResourceAsStream("application.yaml")) {
            Map<String,Object> load = yaml.load(resourceAsStream);
            String o = (String) ((Map) load.get("aaa")).get("bbb");
            System.out.println(o);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
