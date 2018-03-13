package com.zhp.common.exception;

import java.util.HashMap;
import java.util.Map;

public final class ErrorEntity {

    private ErrorCode errorCode;
    private String message;
    private Map<String, String> errorInfo;
    private String stackTrace;

    public ErrorEntity() {

    }

    public ErrorEntity(ErrorCode errorCode, String message) {
        this(errorCode, message, new HashMap<>(), null);
    }

    public ErrorEntity(ErrorCode errorCode, String message, Map<String, String> errorInfo, String stackTrace) {
        this.errorCode = errorCode;
        this.message = message;
        this.errorInfo = errorInfo;
        this.stackTrace = stackTrace;
    }

    public ErrorEntity(ErrorCode errorCode, Map<String, String> errorInfo) {
        this.errorCode = errorCode;
        this.errorInfo = errorInfo;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(Map<String, String> errorInfo) {
        this.errorInfo = errorInfo;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public static ErrorEntity getError(ErrorCode errorCode) {
        return new ErrorEntity(errorCode, errorCode.getMessage());
    }
}
