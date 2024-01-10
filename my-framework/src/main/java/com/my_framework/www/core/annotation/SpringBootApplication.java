package com.my_framework.www.core.annotation;

import java.lang.annotation.*;

/**
 * @author 14629
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SpringBootApplication {
    String scanPackage() default "";
}
