package com.my_framework.www.core.context;

import com.my_framework.www.core.factory.BeanFactory;

/**
 *
 * 空接口，明白就好
 * 原接口需要继承ListableBeanFactory, HierarchicalBeanFactory等等，这里就简单继承BeanFactory
 * @author 14629
 */
public interface ApplicationContext extends BeanFactory {
}
