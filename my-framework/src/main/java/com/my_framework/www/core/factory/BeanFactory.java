package com.my_framework.www.core.factory;

/**
 * @author 14629
 */
public interface BeanFactory {

    /**
     * 根据类名获取bean的实例
     * @param name 被获取bean的类名
     * @return bean 对象
     * @throws Exception 异常
     */
    Object getBean(String name) throws Exception;

    /**
     * 根据类的class获取bean的实例
     * @param requiredType 被获取bean的class
     * @return bean
     * @param <T> 被获取的类
     * @throws Exception 异常
     */
    <T> T getBean(Class<T> requiredType) throws Exception;
    String[] getBeanDefinitionNames();
}