package com.my_framework.www.core.annotation.bean;

import java.lang.annotation.*;

/**
 * @author _qqiu
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
    String value() default "";
}
