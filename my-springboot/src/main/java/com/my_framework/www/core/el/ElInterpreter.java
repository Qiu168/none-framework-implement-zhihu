package com.my_framework.www.core.el;

import com.my_framework.www.core.context.ResourceHolder;
import com.my_framework.www.core.context.ResourceLoader;
import com.my_framework.www.utils.ContextUtil;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ${key名称}：
 * 用户获取外部文件中指定key的值
 * 可以出现在xml配置文件中，也可以出现在注解@Value中
 * 一般用户获取数据库配置文件的内容信息等
 * #{表达式}：
 * 是Spring EL表达式的格式
 * 可以出现在xml配置文件中，也可以出现在注解@Value中
 * 可以任意表达式，支持运算符等
 *
 * @author _qiu
 * 只实现了${}
 */
public class ElInterpreter {
    public static String parsing(String el) {
        ResourceLoader resource = ResourceHolder.getResourceLoader();
        String[] configString = getContentInfo(el);
        for (String s : configString) {
            String substring = s.substring(2, s.length() - 1);
            substring=resource.getValue(substring);
            if(substring==null){
                substring="";
            }
            el = el.replace(s, substring);
        }
        return el;
    }

    public static String parsing(ResourceLoader resourceLoader, String el) {
        String[] configString = getContentInfo(el);
        for (String s : configString) {
            String substring = s.substring(2, s.length() - 1);
            if(resourceLoader.getValue(substring)==null){
                substring="";
            }
            el = el.replace(s, resourceLoader.getValue(substring));
        }
        return el;
    }

    private static String[] getContentInfo(String content) {
        Pattern regex = Pattern.compile("\\$\\{([^}]*)}");
        Matcher matcher = regex.matcher(content);
        ArrayList<String> sql = new ArrayList<>();
        while (matcher.find()) {
            sql.add(matcher.group());
        }
        return sql.toArray(new String[0]);
    }
}
