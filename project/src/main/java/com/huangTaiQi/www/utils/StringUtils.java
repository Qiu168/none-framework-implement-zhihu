package com.huangTaiQi.www.utils;

/**
 * @author 14629
 */
public class StringUtils {
    public static String toClassName(String str){
        return str.substring(0,str.lastIndexOf('@'));
    }
    public static boolean isEmpty(String str){
        if (str != null){
            str = str.trim();
        }
        return null==str||"".equals(str);
    }

    public static String getToken(String token) {
        return token.substring(token.lastIndexOf(':')+1);
    }
}
