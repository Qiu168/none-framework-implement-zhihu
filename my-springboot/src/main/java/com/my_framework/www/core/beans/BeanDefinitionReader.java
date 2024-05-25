package com.my_framework.www.core.beans;

import com.my_framework.www.core.annotation.Configuration;
import com.my_framework.www.core.annotation.stereotype.Component;
import com.my_framework.www.core.beans.factory.BeanLoader;
import com.my_framework.www.utils.ClassUtil;
import com.my_framework.www.utils.StringUtil;
import lombok.Getter;
import lombok.Setter;

import java.lang.annotation.Annotation;
import java.util.*;


/**
 * 读取配置文件，扫描相关的类解析成BeanDefinition
 *
 * @author _qqiu
 */
public class BeanDefinitionReader {
    @Getter
    private final Properties config = new Properties();

    @Getter
    private final List<Class<?>> Configurations = new ArrayList<>();
    private final List<BeanLoader> beanLoaders=new ArrayList<>();
    @Getter
    private final Set<Class<?>> registerBeanClasses;

    public BeanDefinitionReader(String scanPackage) {
        //扫描，扫描资源文件(class)，并保存到集合中
        registerBeanClasses = ClassUtil.getClassSet(scanPackage);
    }

    /**
     * 把配置文件中扫描到的所有的配置信息转换为BeanDefinition对象
     */
    public List<BeanDefinition> loadBeanDefinitions() {
        List<BeanDefinition> result = new ArrayList<>();
        try {
            for (Class<?> beanClass : registerBeanClasses) {
                //如果是一个接口，是不能实例化的，不需要封装
                if (beanClass.isInterface()) {
                    continue;
                }
                for (BeanLoader beanLoader : beanLoaders) {
                    if (beanLoader.isLoad(beanClass)) {
                        BeanDefinition beanDefinition = beanLoader.loadBean(beanClass);
                        result.add(beanDefinition);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 添加 Bean Loader
     */
    public void addBeanLoader(BeanLoader beanLoader){
        beanLoaders.add(beanLoader);
    }

}
