package com.my_framework.www;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.my_framework.www.utils.StringUtil;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class JsonTest {
    @Test
    public void test(){
        String s="{\"usernameOrEmail\":[\"_qqiu09477@qq.com\"],\"password\":[\"123456789\"],\"imgCode\":[\"c734\",\"asdafd\"]}";
        String sb="{\"usernameOrEmail\":\"_qqiu09477@qq.com\",\"password\":\"123456789\",\"imgCode\":\"c734\"}";
        String sb2 = StringUtil.convertToJsonArray(s);
        System.out.println("SB2"+sb2);
        Map<String, String[]> o1 = JSON.parseObject(sb2, new TypeReference<Map<String, String[]>>() {});
        Map<String, String> o = JSON.parseObject(s, new TypeReference<Map<String, String>>() {});
        System.out.println(o);
        System.out.println(o1);
        System.out.println(o.get("imgCode"));
        System.out.println(Arrays.toString(o1.get("imgCode")));
        Map<String,String[]> map=new HashMap<>();
        map.put("img", new String[]{"123", "123"});
        map.put("123", new String[]{"789", "asdafd"});
        System.out.println(map);
        String jsonString = JSON.toJSONString(map);
        Map<String, String[]> o2 = JSON.parseObject(jsonString, new TypeReference<Map<String, String[]>>() {});
        System.out.println(o2);
        System.out.println(jsonString);
        Gson gson = new Gson();

        Map<String, String[]> params = gson.fromJson(s,  new TypeToken<Map<String, String[]>>() {}.getType());

    }
}
