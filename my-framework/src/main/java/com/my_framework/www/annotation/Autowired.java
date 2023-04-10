package com.my_framework.www.annotation;

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
