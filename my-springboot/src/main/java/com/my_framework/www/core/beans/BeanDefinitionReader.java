package com.my_framework.www.core.beans;

import com.my_framework.www.core.annotation.Configuration;
import com.my_framework.www.core.annotation.stereotype.Component;
import com.my_framework.www.core.beans.factory.BeanLoader;
import com.my_framework.www.utils.ClassUtil;
import com.my_framework.www.utils.StringUtil;
import lombok.Getter;

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

    private Set<Class<?>> registerBeanClasses = new HashSet<>();

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
                //todo
                for (BeanLoader beanLoader : beanLoaders) {
                    if (beanLoader.isLoad(beanClass)) {
                        BeanDefinition beanDefinition = beanLoader.loadBean(beanClass);
                        result.add(beanDefinition);
                    }
                }
//                Annotation[] annotations = beanClass.getAnnotations();
//                for (Annotation annotation : annotations) {
//                    Class<? extends Annotation> annotationType = annotation.annotationType();
//                    //只考虑被@Component注解的class
//                    if (annotationType.isAnnotationPresent(Component.class) || annotation instanceof Component) {
//                        //beanName有三种情况:
//                        //1、默认是类名首字母小写
//                        //2、自定义名字（这里暂不考虑）
//                        //3、接口注入
//                        result.add(doCreateBeanDefinition(StringUtil.toLowerFirstCase(beanClass.getSimpleName()), beanClass.getName()));
//                        if (annotation instanceof Configuration) {
//                            Configurations.add(beanClass);
//                        }
//                        break;
//                    }
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 相关属性封装到BeanDefinition
     */
    private BeanDefinition doCreateBeanDefinition(String factoryBeanName, String beanClassName) {
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setFactoryBeanName(factoryBeanName);
        beanDefinition.setBeanClassName(beanClassName);
        return beanDefinition;
    }

}
