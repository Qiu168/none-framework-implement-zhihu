package com.huangTaiQi.www.utils;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author 14629
 */

public class BeanUtil {


    /**
     * 将源对象中的属性值复制到目标对象中
     *使用Java自带的Introspector来获取源对象和目标对象各自的属性列表。
     * 然后，我们遍历两个列表，找到相同属性名和类型的属性，再利用反射获取属性的值，并将其设置到目标对象中。
     * @param source      源对象
     * @param targetClass 目标对象类
     * @param <T>         目标对象类型
     * @return 目标对象
     */
    public static <T> T copyProperties(Object source, Class<T> targetClass) {
        if (Objects.isNull(source) || Objects.isNull(targetClass)) {
            return null;
        }

        T target;
        try {
            target = targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }

        PropertyDescriptor[] sourceProperties = null;
        PropertyDescriptor[] targetProperties = null;

        try {
            sourceProperties = Introspector.getBeanInfo(source.getClass()).getPropertyDescriptors();
            targetProperties = Introspector.getBeanInfo(targetClass).getPropertyDescriptors();
        } catch (IntrospectionException e) {
            e.printStackTrace();
            return null;
        }

        if (sourceProperties == null || targetProperties == null) {
            return null;
        }

        for (PropertyDescriptor sourceProperty : sourceProperties) {
            for (PropertyDescriptor targetProperty : targetProperties) {
                if (sourceProperty.getName().equals(targetProperty.getName()) &&
                        sourceProperty.getPropertyType().equals(targetProperty.getPropertyType())) {
                    Method sourceGetter = sourceProperty.getReadMethod();
                    Method targetSetter = targetProperty.getWriteMethod();
                    if (Objects.isNull(sourceGetter) || Objects.isNull(targetSetter)) {
                        continue;
                    }

                    try {
                        Object propertyValue = sourceGetter.invoke(source);
                        targetSetter.invoke(target, propertyValue);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                        return null;
                    }

                    break;
                }
            }
        }

        return target;
    }

    /**
     * 将JavaBean转换为Map
     *
     * @param bean JavaBean对象
     * @return Map对象
     */
    public static Map<String, String> beanToMap(Object bean) {
        Map<String, String> map = new HashMap<>();

        if (Objects.isNull(bean)) {
            return map;
        }
        PropertyDescriptor[] properties = null;
        try {
            properties = Introspector.getBeanInfo(bean.getClass()).getPropertyDescriptors();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }

        if (properties != null) {
            for (PropertyDescriptor property : properties) {
                String key = property.getName();
                Method getter = property.getReadMethod();
                if (Objects.isNull(getter)) {
                    continue;
                }
                try {
                    Object value = getter.invoke(bean);
                    if(value==null){
                        map.put(key, null);
                    } else{
                        map.put(key, Serializer.serialize(value));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return map;
    }


    public static void fillBeanWithMap(Object bean, Map<String, String> map) throws Exception {
        if (bean == null || map == null) {
            throw new IllegalArgumentException("bean or map cannot be null");
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            if("class".equals(fieldName)){
                continue;
            }
            if (value != null) {
                Field field = bean.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(bean, Serializer.deserialize(value));
            }
        }
    }
}
