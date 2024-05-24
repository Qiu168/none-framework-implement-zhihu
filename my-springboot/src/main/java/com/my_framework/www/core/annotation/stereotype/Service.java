package com.my_framework.www.core.annotation.stereotype;

import java.lang.annotation.*;

/**
 *
 * @author _qqiu
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
@Documented
public @interface Service {
    String value() default "";
}
