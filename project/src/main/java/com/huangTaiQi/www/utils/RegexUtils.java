package com.huangTaiQi.www.utils;

import java.util.regex.Pattern;


/**
 * @author _qqiu
 */
public class RegexUtils {
    public static boolean check(String regex,String str){
        return Pattern.matches(regex,str);
    }

}