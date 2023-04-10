package com.my_framework.www.annotation;

import java.lang.annotation.*;

/**
 * @author 14629
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    String value() default "";
}
