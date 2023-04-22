package com.huangTaiQi.www.utils;

import java.util.regex.Pattern;


/**
 * @author 14629
 */
public class RegexUtils {
    public static boolean check(String regex,String str){
        return Pattern.matches(regex,str);
    }

}