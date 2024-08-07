package com.my_framework.www.webmvc.annotation;

import java.lang.annotation.*;

/**
 * @author _qqiu
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    String value() default "";
    String method() default "get";
}
