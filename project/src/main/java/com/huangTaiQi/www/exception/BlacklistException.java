package com.huangTaiQi.www.exception;

/**
 * 黑名单异常
 * @author _qqiu
 */
public class BlacklistException extends RuntimeException {
    public BlacklistException(String message) {
        super(message);
    }
}