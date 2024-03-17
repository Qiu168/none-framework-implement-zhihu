package com.my_framework.www.core.annotation.bean;

import java.lang.annotation.*;

/**
 * @author _qiu
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Qualifier {
    String value();
}
