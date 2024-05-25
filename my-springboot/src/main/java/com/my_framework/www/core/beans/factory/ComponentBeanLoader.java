package com.my_framework.www.core.beans.factory;

import com.my_framework.www.core.annotation.Configuration;
import com.my_framework.www.core.annotation.stereotype.Component;
import com.my_framework.www.core.beans.BeanDefinition;
import com.my_framework.www.utils.StringUtil;

import java.lang.annotation.Annotation;

@Configuration
public class ComponentBeanLoader implements BeanLoader {
    @Override
    public boolean isLoad(Class<?> clz) {
        for (Annotation annotation : clz.getAnnotations()) {
            Class<? extends Annotation> annotationType = annotation.annotationType();
            // 判断是否是Component注解
            if (annotationType.isAnnotationPresent(Component.class) || annotation instanceof Component) {
                return true;
            }
        }
        return false;
    }

    @Override
    public BeanDefinition loadBean(Class<?> clz) {
        return BeanDefinition.builder()
                .beanClassName(clz.getName())
                .factoryBeanName(StringUtil.toLowerFirstCase(clz.getSimpleName()))
                .build();
    }
}
