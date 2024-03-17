package com.my_framework.www.utils;


/**
 * 类型转换
 * @author 14629
 */
public class CastUtil {

    public static String castString(Object obj){
        return CastUtil.castString(obj,"");
    }


    public static String castString(Object obj,String defaultValue){
        return obj!=null?String.valueOf(obj):defaultValue;
    }


    public static double castDouble(Object obj){
        return CastUtil.castDouble(obj,0);
    }


    public static double castDouble(Object obj,double defaultValue){
        double value = defaultValue;
        //声明结果，把默认值赋给结果
        if (obj!=null){
            //判断是否为null
            String strValue = castString(obj);
            //转换为String
            if (StringUtil.isNotEmpty(strValue)){
                //判断字符串是否为空（是否为空只能判断字符串，不能判断Object）
                try{
                    value = Double.parseDouble(strValue);
                    //不为空则把值赋给value
                }catch (NumberFormatException e){
                    value = defaultValue;
                    //格式不对把默认值赋给value
                }

            }
        }
        return value;
    }


    public static long castLong(Object obj){
        return CastUtil.castLong(obj,0);
    }


    public static long castLong(Object obj,long defaultValue){
        long value = defaultValue;
        //声明结果，把默认值赋给结果
        if (obj!=null){
            //判断是否为null
            String strValue = castString(obj);
            //转换为String
            if (StringUtil.isNotEmpty(strValue)){
                //判断字符串是否为空（是否为空只能判断字符串，不能判断Object）
                try{
                    value = Long.parseLong(strValue);
                    //不为空则把值赋给value
                }catch (NumberFormatException e){
                    value = defaultValue;
                    //格式不对把默认值赋给value
                }

            }
        }
        return value;
    }


    public static int castInt(Object obj){
        return CastUtil.castInt(obj,0);
    }


    public static int castInt(Object obj,int defaultValue){
        int value = defaultValue;
        //声明结果，把默认值赋给结果
        if (obj!=null){
            //判断是否为null
            String strValue = castString(obj);
            //转换为String
            if (StringUtil.isNotEmpty(strValue)){
                //判断字符串是否为空（是否为空只能判断字符串，不能判断Object）
                try{
                    value = Integer.parseInt(strValue);
                    //不为空则把值赋给value
                }catch (NumberFormatException e){
                    value = defaultValue;
                    //格式不对把默认值赋给value
                }

            }
        }
        return value;
    }

    public static boolean castBoolean(Object obj){
        return CastUtil.castBoolean(obj,false);
    }


    public static boolean castBoolean(Object obj,boolean defaultValue){
        boolean value = defaultValue;
        if (obj!=null){
            //为null则返回默认值
            value = Boolean.parseBoolean(castString(obj));
            //底层会把字符串和true对比，所以不用判断是否为空字符串
        }
        return value;
    }
}