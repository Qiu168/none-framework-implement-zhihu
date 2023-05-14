package com.huangTaiQi.www.exception;

/**
 * 黑名单异常
 * @author 14629
 */
public class BlacklistException extends RuntimeException {
    public BlacklistException(String message) {
        super(message);
    }
}