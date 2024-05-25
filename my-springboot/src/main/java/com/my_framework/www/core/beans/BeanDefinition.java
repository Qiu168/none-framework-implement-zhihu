package com.my_framework.www.core.beans;

import com.my_framework.www.core.annotation.bean.Scope;
import lombok.*;

import javax.annotation.Nullable;

/**
 * Bean的配置信息
 * @author _qqiu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    @Nullable
    /*xxx: 依赖的bean*/
    private String[] dependsOn;
    private String scope;
    private ConstructorFunction constructorFunction;

}
