package com.my_framework.www.security.annotation;


import java.lang.annotation.*;


/**
 * 权限管理
 * @author _qqiu
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Access {
    long rightName();
    String message() default "没有权限";
}
