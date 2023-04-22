package com.my_framework.www.annotation;

/**
 * @author 14629
 */
public @interface Pattern {
    /**
     * 默认为非空
     * @return 正则
     */
    String regex() default "/^.+$/";
    String message() default "notNull";
}
