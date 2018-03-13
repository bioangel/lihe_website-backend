package com.zhp.common.exception;

import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseResponseException extends RuntimeException {

    private final HttpStatus status;
    private final ErrorCode errorCode;
    private final Map<String, String> errorMessages;
    private String stackTraceInfo;

    public BaseResponseException(HttpStatus status) {
        this(status, ErrorCode.UNKNOWN, new HashMap<>());
    }

    public BaseResponseException(ErrorCode errorCode) {
        this(errorCode.getStatus(), errorCode, new HashMap<>());
    }

    public BaseResponseException(ErrorCode errorCode, String msg) {
        super(msg);
        this.status = errorCode.getStatus();
        this.errorCode = errorCode;
        this.errorMessages = Collections.emptyMap();
        this.stackTraceInfo = msg;
    }

    public BaseResponseException(HttpStatus status, ErrorCode errorCode) {
        this(status, errorCode, new HashMap<>());
    }

    public BaseResponseException(HttpStatus status, ErrorCode errorCode,
                                 Map<String, String> errorMessages) {
        super(errorCode.getMessage());
        this.status = status;
        this.errorCode = errorCode;
        this.errorMessages = errorMessages;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public Map<String, String> getErrorMessages() {
        return errorMessages;
    }

    public String getStackTraceInfo() {
        return stackTraceInfo;
    }
}
