package com.my_framework.www.utils;

import java.util.*;

/**
 * @author 14629
 */
public class CollectionUtil {
    
    /**
     * 判断集合是否为空
     * @param collection 集合
     * @return boolean
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }
    /**
     * 判断Map是否为空
     * @param map map
     * @return boolean
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }
    /**
     * 将数组转为List
     * @param array 数组
     * @return List
     */
    public static <T> List<T> arrayToList(T[] array) {
        if (array == null || array.length == 0) {
            return Collections.emptyList();
        }
        List<T> list = new ArrayList<>(array.length);
        list.addAll(Arrays.asList(array));
        return list;
    }
    
    /**
     * 将List转为数组
     * @param list List
     * @return Object[]
     */
    public static Object[] listToArray(List list) {
        if (isEmpty(list)) {
            return new Object[0];
        }
        Object[] array = new Object[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }
    
    /**
     * 将Set转为数组
     * @param set Set
     * @return Object[]
     */
    public static Object[] setToArray(Set<?> set) {
        if (isEmpty(set)) {
            return new Object[0];
        }
        Object[] array = new Object[set.size()];
        int i = 0;
        for (Object obj : set) {
            array[i++] = obj;
        }
        return array;
    }
    
    /**
     * 判断两个集合是否相等
     * @param collection1 集合1
     * @param collection2 集合2
     * @return boolean
     */
    public static boolean isEqualCollection(Collection<?> collection1, Collection<?> collection2) {
        if (collection1 == null && collection2 == null) {
            return true;
        }
        if (collection1 == null || collection2 == null || collection1.size() != collection2.size()) {
            return false;
        }
        return collection1.containsAll(collection2) && collection2.containsAll(collection1);
    }
    
    /**
     * 判断两个Map是否相等
     * @param map1 Map1
     * @param map2 Map2
     * @return boolean
     */
    public static boolean isEqualMap(Map<?, ?> map1, Map<?, ?> map2) {
        if (map1 == null && map2 == null) {
            return true;
        }
        if (map1 == null || map2 == null || map1.size() != map2.size()) {
            return false;
        }
        for (Map.Entry<?, ?> entry : map1.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (!map2.containsKey(key)) {
                return false;
            }
            Object v = map2.get(key);
            if (!Objects.equals(value, v)) {
                return false;
            }
        }
        return true;
    }
}
