package com.huangTaiQi.www.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

public class Serializer {

    /**
     * 序列化
     * @param obj 被序列化的对象
     * @return 返回序列化后的string
     * @throws Exception 异常
     */
    public static String serialize(Object obj) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        return Base64.getEncoder().encodeToString(bos.toByteArray());
    }

    /**
     * 反序列化
     * @param str 反序列化的字符串
     * @return 返回obj
     * @throws Exception 异常
     */

    public static Object deserialize(String str) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(str.getBytes());
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        return ois.readObject();
    }
}