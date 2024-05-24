package com.my_framework.www.utils;
 

 
/**
 * desc : 数组工具类
 * @author _qqiu
 */
public final class ArrayUtil {
 
    /**
     * 判断数组是否非空
     */
    public static boolean isNotEmpty(Object[] array){
        return !isEmpty(array);
    }
 
    /**
     * 判断数组是否为空
     */
    public static boolean isEmpty(Object[] array){
        return array==null||array.length==0;
    }
 
}