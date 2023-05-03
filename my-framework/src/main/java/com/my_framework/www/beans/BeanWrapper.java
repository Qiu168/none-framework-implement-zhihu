package com.my_framework.www.beans;


/**
 * Bean配置信息被读取并实例化成一个实例后，这个实例封装在BeanWrapper中
 * 将bean的实例化对象存进BeanWrapper这个类的作用主要是为了在后续的操作中，
 * 更方便地对bean进行一些加强或增强的操作，例如动态代理、AOP等操作。
 * 通过BeanWrapper，可以更方便地获取bean实例的Class对象，从而实现对bean实例的操作。
 * @author 14629
 */
public class BeanWrapper {
    /**
     * Bean的实例化对象
     */
    private Object wrappedObject;

    public BeanWrapper(Object wrappedObject) {
        this.wrappedObject = wrappedObject;
    }

    public void setWrappedObject(Object wrappedObject) {
        this.wrappedObject = wrappedObject;
    }

    public Object getWrappedInstance() {
        return this.wrappedObject;
    }

    public Class<?> getWrappedClass() {
        return getWrappedInstance().getClass();
    }

}
