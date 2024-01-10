package com.huangTaiQi.www.utils.code;


import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author 14629
 */
public class Base64Utils {
    public static String encode(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
    }
    public static String decode(String encodedStr) {
        return new String(Base64.getDecoder().decode(encodedStr), StandardCharsets.UTF_8);
    }
}
