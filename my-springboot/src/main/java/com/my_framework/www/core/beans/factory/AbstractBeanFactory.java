package com.my_framework.www.core.beans.factory;

import com.my_framework.www.core.annotation.Configuration;
import com.my_framework.www.core.annotation.bean.Autowired;
import com.my_framework.www.core.annotation.bean.Qualifier;
import com.my_framework.www.core.annotation.bean.Value;
import com.my_framework.www.core.annotation.stereotype.Component;
import com.my_framework.www.core.annotation.stereotype.Service;
import com.my_framework.www.core.aop.AopProxy;
import com.my_framework.www.core.aop.CglibAopProxy;
import com.my_framework.www.core.aop.JdkDynamicAopProxy;
import com.my_framework.www.core.aop.config.AopConfig;
import com.my_framework.www.core.aop.support.AdvisedSupport;
import com.my_framework.www.core.beans.BeanDefinition;
import com.my_framework.www.core.beans.BeanDefinitionReader;
import com.my_framework.www.core.beans.BeanWrapper;
import com.my_framework.www.core.beans.ConfigurationExecution;
import com.my_framework.www.core.context.Impl.DefaultResourceLoader;
import com.my_framework.www.core.context.ResourceHolder;
import com.my_framework.www.core.context.ResourceLoader;
import com.my_framework.www.core.el.ElInterpreter;
import com.my_framework.www.db.annotation.Transaction;
import com.my_framework.www.db.transaction.ServiceProxyFactory;
import com.my_framework.www.exception.DynamicBeanNotKnowWhichAutowireException;
import com.my_framework.www.utils.Assert;
import com.my_framework.www.utils.PropsUtil;
import com.my_framework.www.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author _qiu
 */
@Slf4j
public abstract class AbstractBeanFactory implements BeanFactory{
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    private final Map<String, BeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<>();
    private final Map<Class<?>, List<BeanWrapper>> interfaceBeanInstanceCache = new ConcurrentHashMap<>();
//    private final List<Object> configurations= new ArrayList<>();
    /**
     * 配置执行链
     */
    private final List<ConfigurationExecution> configurationExecutionChain=new ArrayList<>();
    protected ResourceLoader resourceLoader;

    /**
     * 将beanDefinitions注册成map
     * @param beanDefinitions beanDefinitions
     */
    protected void doRegisterBeanDefinition(List<BeanDefinition> beanDefinitions) throws Exception {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            if (beanDefinitionMap.containsKey(beanDefinition.getFactoryBeanName())) {
                throw new Exception("The \"" + beanDefinition.getFactoryBeanName() + "\" is exists!!");
            }
            beanDefinitionMap.put(beanDefinition.getFactoryBeanName(), beanDefinition);
        }
    }

    @Override
    public Object getBean(String beanName) {
        //如果是单例，那么在上一次调用getBean获取该bean时已经初始化过了，拿到不为空的实例直接返回即可
        Object instance = getSingleton(beanName);
        if (instance != null) {
            return instance;
        }
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        Assert.notNull(beanDefinition,"找不到bean："+beanName);
        //1.调用反射初始化Bean
        instance = instantiateBean(beanDefinition);
        //2.把这个对象封装到BeanWrapper中
        BeanWrapper beanWrapper = new BeanWrapper(instance);
        //3.把BeanWrapper保存到IOC容器中去
        //注册一个类名（首字母小写，如helloService）
        factoryBeanInstanceCache.put(beanName, beanWrapper);
        //注册一个全类名（如com.huangTaiQi.www.HelloService）
        factoryBeanInstanceCache.put(beanDefinition.getBeanClassName(), beanWrapper);
        //接口
        for (Class<?> i : instance.getClass().getInterfaces()) {
            List<BeanWrapper> list = interfaceBeanInstanceCache.getOrDefault(i, new ArrayList<>());
            list.add(beanWrapper);
            interfaceBeanInstanceCache.put(i,list);
        }
        //4.注入
        populateBean(beanWrapper);
        return this.factoryBeanInstanceCache.get(beanName).getWrappedInstance();
    }


    private Object getSingleton(String beanName) {
        if(this.factoryBeanInstanceCache.containsKey(beanName)){
            return factoryBeanInstanceCache.get(beanName).getWrappedInstance();
        }
        return null;
    }
    protected void doAutowired() {
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : beanDefinitionMap.entrySet()) {
            String beanName = beanDefinitionEntry.getKey();
            if (!beanDefinitionEntry.getValue().isLazyInit()) {
                try {
                    getBean(beanName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 注入bean
     *
     * @param beanWrapper bean的实例
     */
    private void populateBean(BeanWrapper beanWrapper) {
        Class<?> clazz = beanWrapper.getWrappedClass();
        if (Enhancer.isEnhanced(clazz)) {
            //todo 是cglib代理
        }
        //获得所有的成员变量
        Field[] fields = clazz.getDeclaredFields();
        Class<?> superclass = clazz.getSuperclass();
        Field[] superclassFields = superclass.getDeclaredFields();
        Field[] result = new Field[fields.length + superclassFields.length];
        System.arraycopy(fields, 0, result, 0, fields.length);
        System.arraycopy(superclassFields, 0, result, fields.length, superclassFields.length);
        for (Field field : result) {
            if(field.isAnnotationPresent(Value.class)){
                populateValue(beanWrapper.getWrappedInstance(),field);
            }
            //如果没有被Autowired注解的成员变量则直接跳过
            if (!field.isAnnotationPresent(Autowired.class)) {
                continue;
            }
            Autowired autowired = field.getAnnotation(Autowired.class);
            //拿到需要注入的类名
            String autowiredBeanName = autowired.value().trim();
            Class<?> autowiredBeanType=field.getType();;
            if (autowiredBeanName.isEmpty()) {
                autowiredBeanName = autowiredBeanType.getName();
            }
            //log.info(beanWrapper.getWrappedInstance().getClass()+"  autowired:"+autowiredBeanName);
            //强制访问该成员变量
            field.setAccessible(true);
            if(autowiredBeanType.isInterface()){
                //todo
                List<BeanWrapper> instances = interfaceBeanInstanceCache.get(autowiredBeanType);
                if(instances==null|| instances.isEmpty()){
                    continue;
                }
                if(instances.size()!=1&&field.getAnnotation(Qualifier.class)==null){
                    throw new DynamicBeanNotKnowWhichAutowireException(instances.stream().map(BeanWrapper::getWrappedClass).collect(Collectors.toList()));
                }
                if(instances.size()==1){
                    autowiredBeanName=instances.get(0).getWrappedClass().getName();
                }else{
                    autowiredBeanName=field.getAnnotation(Qualifier.class).value();
                }
            }
            try {
                String simpleBeanName = StringUtil.getBeanName(autowiredBeanName);
                BeanDefinition definition = beanDefinitionMap.get(simpleBeanName);
                if(definition!=null){
                    //此成员变量是ioc控制的，但是还未实例化
                    if (factoryBeanInstanceCache.get(autowiredBeanName) == null) {
                        //被注入的成员变量还没有实例化
                        //logger.log(Level.INFO,"实例化成员变量");
                        getBean(simpleBeanName);
                    }
                }else {
                    //此成员变量不是ioc控制的
                    continue;
                }
                //将容器中的实例注入到成员变量中
                field.set(beanWrapper.getWrappedInstance(), factoryBeanInstanceCache.get(autowiredBeanName).getWrappedInstance());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void populateValue(Object instance,Field field) {
        if(field.getType()!=String.class){
            return;
        }
        Value value = field.getAnnotation(Value.class);
        String el = value.value();
        String parsing = ElInterpreter.parsing(el);
        try {
            field.set(instance,parsing);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 使用cast进行类型转换时，会在编译期进行类型检查，可以在一定程度上避免在运行时出现类型转换异常。
     * @param requiredType 被获取bean的class
     * @return bean对象
     * @param <T> bean的类型
     */
    @Override
    public <T> T getBean(Class<T> requiredType) {
        String beanName=requiredType.getName();
        return requiredType.cast(getBean(beanName));
    }

    public String[] getBeanDefinitionNames() {
        return beanDefinitionMap.keySet().toArray(new String[0]);
    }
    /**
     * todo: 重构
     * @param beanDefinition b
     * @return
     */
    private Object instantiateBean(BeanDefinition beanDefinition) {
        //1、拿到要实例化的对象的类名
        String className = beanDefinition.getBeanClassName();
        //2、反射实例化，得到一个对象
        Object instance = null;
        try {
            Class<?> clazz = Class.forName(className);
            if(clazz.isAnnotationPresent(Service.class)){
                //如果是service,检查是否有被@Transaction注释的方法
                Method[] methods= clazz.getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(Transaction.class)) {
                        //如果有，代理
                        instance = ServiceProxyFactory.getProxy(clazz);
                        break;
                    }
                }
            }
            if(instance==null){
                //没有被代理的类直接实例化
                instance = clazz.newInstance();
            }
//            //获取AOP配置
//            AdvisedSupport config = getAopConfig();
//            config.setTargetClass(clazz);
//            //这个instance应该被注入，否则成员变量为空
//            config.setTarget(instance);
//            //符合PointCut的规则的话，将创建代理对象
//            if(config.pointCutMatch()) {
//                //把这个对象封装到BeanWrapper中
//                BeanWrapper beanWrapper = new BeanWrapper(instance);
//                //注入,不会重复注入，因为被代理类不会返回
//                populateBean(beanWrapper);
//                config.setTarget(beanWrapper.getWrappedInstance());
//                //创建代理
//                instance = createProxy(config).getProxy();
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }
    private AopProxy createProxy(AdvisedSupport config) {
        Class<?> targetClass = config.getTargetClass();
        //如果接口数量 > 0则使用JDK原生动态代理
        if(targetClass.getInterfaces().length > 0){
            return new JdkDynamicAopProxy(config);
        }
        return new CglibAopProxy(config);
    }
    private AdvisedSupport getAopConfig() {
        AopConfig config = new AopConfig();
        Properties aopProp = PropsUtil.loadProps("aop.properties");
        config.setPointCut(PropsUtil.getString(aopProp,"pointCut"));
        config.setAspectClass(PropsUtil.getString(aopProp,"aspectClass"));
        config.setAspectBefore(PropsUtil.getString(aopProp,"aspectBefore"));
        config.setAspectAfter(PropsUtil.getString(aopProp,"aspectAfter"));
        config.setAspectAfterThrow(PropsUtil.getString(aopProp,"aspectAfterThrow"));
        config.setAspectAfterThrowingName(PropsUtil.getString(aopProp,"aspectAfterThrowingName"));
        return new AdvisedSupport(config);
    }

    protected void doRefresh(String scanPackage) throws Exception {
        //1、定位，定位配置文件
        resourceLoader=new DefaultResourceLoader();
        ResourceHolder.setResourceLoader(resourceLoader);
        //包扫描
        BeanDefinitionReader reader = new BeanDefinitionReader(scanPackage);
        //先解析Configuration
        registerConfigBean(reader.getRegisterBeanClasses());
        //2、加载配置文件，扫描相关的类，把它们封装成BeanDefinition
        List<BeanDefinition> beanDefinitions = reader.loadBeanDefinitions();
        //3、注册，把配置信息放到容器里面(伪IOC容器)
        doRegisterBeanDefinition(beanDefinitions);
        //4、把不是延时加载的类，提前初始化
        doAutowired();
        log.info("refresh finish");
        log.info(factoryBeanInstanceCache.toString());
    }

    /**
     * 注册配置Bean Configuration
     */
    private void registerConfigBean(Set<Class<?>> registerBeanClasses) throws InstantiationException, IllegalAccessException {
        for (Class<?> clz : registerBeanClasses) {
            if(clz.isAnnotationPresent(Configuration.class)){
                //解析Configuration
                Object instance = clz.newInstance();
                for (ConfigurationExecution configurationExecution : configurationExecutionChain) {
                    configurationExecution.execute(instance);
                }
            }
        }
    }
}
