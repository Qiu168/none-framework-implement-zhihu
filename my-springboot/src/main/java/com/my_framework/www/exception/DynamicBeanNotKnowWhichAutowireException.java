package com.my_framework.www.exception;

import java.util.List;

/**
 * @author 14629
 */
public class DynamicBeanNotKnowWhichAutowireException extends RuntimeException{
    private final List<Class<?>> beans;

    @Override
    public String getMessage() {
        return "dynamic beans: "+beans.toString()+" not know which to autowire";
    }

    public DynamicBeanNotKnowWhichAutowireException(List<Class<?>> beans) {
        super();
        this.beans = beans;
    }
}
