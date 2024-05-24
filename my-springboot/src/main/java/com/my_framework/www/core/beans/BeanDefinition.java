package com.my_framework.www.core.beans;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;

/**
 * Bean的配置信息
 * @author _qqiu
 */
public class BeanDefinition {
    /**
     * 实现类的全类名
     */
    @Setter
    @Getter
    private String beanClassName;
    /**
     * 是否懒加载
     */
    @Setter
    @Getter
    private boolean lazyInit = false;

    /**
     * 保存实现类的类名（首字母小写）
     * 接口名
     */
    @Setter
    @Getter
    private String factoryBeanName;
    @Nullable
    /*xxx: 依赖的bean*/
    private String[] dependsOn;


}
