package com.my_framework.www.webmvc.annotation;

import java.lang.annotation.*;

/**
 * @author 14629
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Pattern {
    /**
     * 默认为非空
     * @return 正则
     */
    String regex() default ".+";
    String message() default "notNull";
}
