package com.huangTaiQi.www.utils.code;


import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * @author 14629
 */
public class Base64Utils {
    public static String encode(String str) throws UnsupportedEncodingException {
        return Base64.getEncoder().encodeToString(str.getBytes("utf-8"));
    }
    public static String decode(String encodedStr) throws UnsupportedEncodingException {
        return new String(Base64.getDecoder().decode(encodedStr),"utf-8");
    }
}
