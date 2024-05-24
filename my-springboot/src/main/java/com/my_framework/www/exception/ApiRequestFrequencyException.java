package com.my_framework.www.exception;

/**
 * @author _qqiu
 */
public class ApiRequestFrequencyException extends RuntimeException {
    
    private final String user;
    
    public ApiRequestFrequencyException(String userId) {
        this.user = userId;
    }

    @Override
    public String getMessage() {
        return "User " + user + " has sent too many API requests.";
    }
}