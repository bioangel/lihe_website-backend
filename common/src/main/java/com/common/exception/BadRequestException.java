package com.common.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class BadRequestException extends BaseResponseException {

    private String request;
    private String response;
    private String api;

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public BadRequestException() {
        super(HttpStatus.BAD_REQUEST);
    }

    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }

    public BadRequestException(ErrorCode errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public BadRequestException(ErrorCode errorCode, Map<String, String> errorMessages) {
        super(HttpStatus.BAD_REQUEST, errorCode, errorMessages);
    }

    public BadRequestException(ErrorEntity errorEntity) {
        super(HttpStatus.BAD_REQUEST, errorEntity.getErrorCode(), errorEntity.getErrorInfo());
    }

}
