package com.my_framework.www.core.beans.factory;

import com.my_framework.www.core.beans.BeanDefinition;

public interface BeanLoader {
    boolean isLoad(Class<?> clz);
    BeanDefinition loadBean(Class<?> clz);
}
