package com.huangTaiQi.www.model.vo;

/**
 * @author 14629
 */
public class ResponseVO<T> {
    /**
     * 状态码
     */
    private final Integer code;
    /**
     * 提示信息
     */
    private final String message;
    /**
     * 数据
     */
    private final T data;

    public ResponseVO(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}