package com.huangTaiQi.www.model.vo;

/**
 * @author 14629
 */
public class IsSuccessVO {
    private Boolean isSuccess;
    private String message;

    public IsSuccessVO() {
    }

    public IsSuccessVO(Boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
