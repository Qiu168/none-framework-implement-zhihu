package com.my_framework.www.webmvc.annotation;

import java.lang.annotation.*;

/**
 * @author 14629
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParam {
    String value() default "";
}
