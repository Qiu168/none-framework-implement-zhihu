package com.my_framework.www.annotation;


import java.lang.annotation.*;

/**
 * 切面注解
 * @author 14629
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    Class<? extends Annotation> value();
 
}
