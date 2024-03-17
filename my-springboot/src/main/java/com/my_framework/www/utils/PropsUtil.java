package com.my_framework.www.utils;



import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 属性文件工具类
 * @author 14629
 */
@Slf4j
public class PropsUtil {
    private static final Logger LOGGER = Logger.getLogger(PropsUtil.class.getName());
    public static Properties loadProps(String fileName) {
        Properties props = new Properties();
        InputStream is = null;
        try {
            //将资源文件加载为流
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if(is==null){
                log.warn(fileName+"file is not Found");
            }else{
                props.load(is);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,"load properties file failure",e);
            throw new RuntimeException("load properties file failure", e);
        }finally {
            if(is !=null){
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE,"close input stream failure",e);
                }
            }
        }
        return props;
    }


    public static String getString(Properties props,String key){
        return getString(props,key,"");
    }


    public static String getString(Properties props,String key,String defaultValue){
        String value = defaultValue;
        if (props.containsKey(key)){
            value = props.getProperty(key);
        }
        return value;
    }


    public static int getInt(Properties props,String key){
        return getInt(props,key,0);
    }


    public static int getInt(Properties props,String key,int defaultValue){
        int value = defaultValue;
        if (props.containsKey(key)){
            value = CastUtil.castInt(props.getProperty(key));
        }
        return value;
    }


    public static boolean getBoolean(Properties props,String key){
        return getBoolean(props,key,false);
    }


    public static boolean getBoolean(Properties props,String key,Boolean defaultValue){
        boolean value = defaultValue;
        if (props.containsKey(key)){
            value = CastUtil.castBoolean(props.getProperty(key));
        }
        return value;
    }
}