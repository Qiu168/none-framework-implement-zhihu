package com.my_framework.www.db.annotation;

import java.lang.annotation.*;

/**
 * @author 14629
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Transaction {
}
