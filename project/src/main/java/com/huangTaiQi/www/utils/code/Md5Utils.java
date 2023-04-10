package com.huangTaiQi.www.utils.code;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author 14629
 * 网上找的轮子
 */
public class Md5Utils {
    public static String encode(String str) throws NoSuchAlgorithmException {
        String salt = "*&(^*^$&*^*(&*51a3";
        str+=salt;
        //生成一个MD5加密计算摘要
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes());
        // 16是表示转换为16进制数
        return new BigInteger(1, md.digest()).toString(16);
    }
}
