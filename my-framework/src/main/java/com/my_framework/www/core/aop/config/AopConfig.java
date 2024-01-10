package com.my_framework.www.core.aop.config;


/**
 * @author 14629
 */
public class AopConfig {

    /**
     * 切点表达式
     */

    private String pointCut;

    /**
     * 前置通知方法
     */
    private String aspectBefore;

    /**
     * 后置通知方法
     */
    private String aspectAfter;

    /**
     * 切面类
     */
    private String aspectClass;

    /**
     * 异常通知方法
     */
    private String aspectAfterThrow;

    /**
     * 抛出的异常类型
     */
    private String aspectAfterThrowingName;

    public String getPointCut() {
        return pointCut;
    }

    public void setPointCut(String pointCut) {
        this.pointCut = pointCut;
    }

    public String getAspectBefore() {
        return aspectBefore;
    }

    public void setAspectBefore(String aspectBefore) {
        this.aspectBefore = aspectBefore;
    }

    public String getAspectAfter() {
        return aspectAfter;
    }

    public void setAspectAfter(String aspectAfter) {
        this.aspectAfter = aspectAfter;
    }

    public String getAspectClass() {
        return aspectClass;
    }

    public void setAspectClass(String aspectClass) {
        this.aspectClass = aspectClass;
    }

    public String getAspectAfterThrow() {
        return aspectAfterThrow;
    }

    public void setAspectAfterThrow(String aspectAfterThrow) {
        this.aspectAfterThrow = aspectAfterThrow;
    }

    public String getAspectAfterThrowingName() {
        return aspectAfterThrowingName;
    }

    public void setAspectAfterThrowingName(String aspectAfterThrowingName) {
        this.aspectAfterThrowingName = aspectAfterThrowingName;
    }
}
