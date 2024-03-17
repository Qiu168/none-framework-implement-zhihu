package com.my_framework.www.core.context.Impl;

import com.my_framework.www.core.context.ResourceLoader;
import com.my_framework.www.core.factory.AbstractBeanFactory;
import com.my_framework.www.core.beans.BeanDefinition;
import com.my_framework.www.core.beans.BeanDefinitionReader;
import com.my_framework.www.core.context.ApplicationContext;

import java.util.*;


/**
 * @author 14629
 */
public class GenericApplicationContext extends AbstractBeanFactory implements ApplicationContext {
    private final String scanPackage;

    public GenericApplicationContext(String scanPackage) {
        this.scanPackage=scanPackage;
        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refresh() throws Exception {
        super.doRefresh(scanPackage);
    }

    @Override
    public ResourceLoader getResource() {
        return resourceLoader;
    }
}
