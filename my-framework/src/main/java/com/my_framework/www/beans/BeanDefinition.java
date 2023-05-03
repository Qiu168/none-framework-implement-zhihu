package com.my_framework.www.beans;

/**
 * Bean的配置信息
 * @author 14629
 */
public class BeanDefinition {
    /**
     * 实现类的全类名
     */
    private String beanClassName;
    /**
     * 是否懒加载
     */
    private boolean lazyInit = false;

    /**
     * 保存实现类的类名（首字母小写）
     * 接口名
     */
    private String factoryBeanName;

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }


    public boolean isLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    public String getFactoryBeanName() {
        return factoryBeanName;
    }

    public void setFactoryBeanName(String factoryBeanName) {
        this.factoryBeanName = factoryBeanName;
    }

}
