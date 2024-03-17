package com.my_framework.www.utils;

import com.my_framework.www.core.context.ApplicationContext;

/**
 * @author 14629
 */
public class ContextUtil  {
    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext)  {
        ContextUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 根据类型获取Bean
     * @param clazz class
     * @param <T> T
     * @return 对象
     */
    public static <T> T getBean(Class<T> clazz)  {
        try {
            return applicationContext.getBean(clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据组件名称获取Bean
     * @param beanName beanName
     * @return 对象
     */
    public static Object getBean(String beanName)  {
        try {
            return applicationContext.getBean(beanName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}