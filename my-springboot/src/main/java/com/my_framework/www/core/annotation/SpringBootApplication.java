package com.my_framework.www.core.annotation;

import java.lang.annotation.*;

/**
 * @author _qqiu
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SpringBootApplication {
    String scanPackage() default "";
}
