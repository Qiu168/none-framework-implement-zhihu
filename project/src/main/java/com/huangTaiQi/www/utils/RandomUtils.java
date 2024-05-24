package com.huangTaiQi.www.utils;


import java.util.Random;

/**
 * @author _qqiu
 */
public class RandomUtils {

    private static final String ALL_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final Random RANDOM = new Random();

    /**
     * 生成一个指定范围内的随机整数
     * @param min 最小值
     * @param max 最大值
     * @return 生成的随机整数
     */
    public static int getRandomInt(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }

    /**
     * 生成一个指定长度的随机字符串
     * @param length 字符串长度
     * @return 生成的随机字符串
     */
    public static String getRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(ALL_CHARS.length());
            sb.append(ALL_CHARS.charAt(randomIndex));
        }
        return sb.toString();
    }
}

