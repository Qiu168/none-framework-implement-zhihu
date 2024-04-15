package com.my_framework.www.core.annotation.aop;


import java.lang.annotation.*;

/**
 * 切面注解
 * @author _qqiu
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
}
