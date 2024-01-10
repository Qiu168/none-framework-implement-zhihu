package com.my_framework.www.core.annotation.stereotype;

import java.lang.annotation.*;

/**
 * @author 14629
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Controller {
    String value() default "";
}
