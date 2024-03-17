package com.my_framework.www.core.annotation.bean;

import java.lang.annotation.*;

/**
 * @author 14629
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
    String value() default "";
}
