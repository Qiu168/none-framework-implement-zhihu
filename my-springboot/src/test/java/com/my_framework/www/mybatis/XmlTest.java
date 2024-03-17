package com.my_framework.www.mybatis;

import com.my_framework.www.db.orm.MapperConfiguration;
import com.my_framework.www.db.orm.XMLConfigBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;

public class XmlTest {
    @Test
    public void testFindXml() throws FileNotFoundException {
        String baseDir = "src/test/resources";  // 你的资源目录，根据实际情况修改
        String pattern = "mapper/*.xml";
        Collection<File> files = FileUtils.listFiles(new File(baseDir), new String[]{"xml"}, true);

        for (File file : files) {
            System.out.println("Resource: " + file.getPath());

            if (file.getPath().matches(".*\\\\"+ pattern.replace("/","\\\\").replace("*", ".*")+"$")) {
                // 处理匹配到的资源
                FileInputStream fileInputStream = new FileInputStream(file);

            }
        }
    }
    @Test
    public void testParseXml(){
        MapperConfiguration configuration = new XMLConfigBuilder().parse();
        System.out.println(configuration);
    }
}
