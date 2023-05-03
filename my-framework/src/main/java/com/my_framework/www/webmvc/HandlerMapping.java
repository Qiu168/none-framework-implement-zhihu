package com.my_framework.www.webmvc;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.regex.Pattern;


/**
 * HandlerMapping保存了Controller实例、所有浏览器能访问到的方法，以及使用@RequestMapping定义的URL表达式
 * @author 14629
 */
public class HandlerMapping {

    /**
     * 保存方法对应的实例
     */
    private Object controller;

    /**
     * 保存映射的方法
     */
    private Method method;

    /**
     * URL的正则匹配
     */
    private Pattern pattern;

    public HandlerMapping(Object controller, Method method, Pattern pattern) {
        this.controller = controller;
        this.method = method;
        this.pattern = pattern;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HandlerMapping that = (HandlerMapping) o;
        return Objects.equals(controller, that.controller) && Objects.equals(method, that.method) && Objects.equals(pattern, that.pattern);
    }

    @Override
    public int hashCode() {
        return Objects.hash(controller, method, pattern);
    }
}
