package com.my_framework.www.db.annotation;

import java.lang.annotation.*;

/**
 * @author _qqiu
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Transaction {
}
