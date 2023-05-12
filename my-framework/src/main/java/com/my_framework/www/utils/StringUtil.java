package com.my_framework.www.utils;


import com.alibaba.fastjson.JSONObject;



/**
 * @author 14629
 */
public class StringUtil {
    public static boolean isEmpty(String str){
        if (str != null){
            str = str.trim();
        }
        return null==str||"".equals(str);
    }

    /**
     * 判断字符串是否为非空
     */
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    };

    /**
     * 将单词首字母变为小写
     */
    public static String toLowerFirstCase(String simpleName) {
        char [] chars = simpleName.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
    /**
     * 分开字符
     */
    public static String[] splitString(String str, String regex){
        return str.split(regex);
    }

    /**
     * 从全类名中得到类名
     * @param name 全类名
     * @return 首字母小写的类名
     */
    public static String getBeanName(String name) {
        return toLowerFirstCase(name.substring(name.lastIndexOf('.') + 1));
    }

    public static String convertToJsonArray(String jsonString) {
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        JSONObject resultObject = new JSONObject();
        for (String key : jsonObject.keySet()) {
            String[] arr = new String[] {jsonObject.getString(key)};
            resultObject.put(key, arr);
        }
        return resultObject.toJSONString();
    }
}
