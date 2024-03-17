package com.my_framework.www.core.annotation;

import com.my_framework.www.core.annotation.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author _qiu
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Configuration {
}
