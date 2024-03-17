package com.my_framework.www.core.annotation;

import java.lang.annotation.*;

/**
 * @author _qiu
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Import {
    Class<?>[] value();
}
