package com.my_framework.www.core.beans;


/**
 * Bean配置信息被读取并实例化成一个实例后，这个实例封装在BeanWrapper中
 * 被包装的对象可以变引用。引用此包装类。
 * @author _qiu
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
