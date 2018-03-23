package com.zhp.common.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.io.IOException;

@Component
@ControllerAdvice
public class ExceptionMapperHandler {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorEntity> exceptionHandler(Exception exception) {
        logger.error("ResponseException exception: {}", ExceptionUtils.getStackTrace(exception));
        return new ResponseEntity<>(new ErrorEntity(ErrorCode.UNKNOWN, exception.getMessage(),
                null, ExceptionUtils.getStackTrace(exception)), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorEntity> exceptionHandler(IOException exception) {
        logger.error("ResponseException exception: {}", ExceptionUtils.getStackTrace(exception));
        return new ResponseEntity<>(new ErrorEntity(ErrorCode.IO_ERROR, exception.getMessage(),
                null, null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorEntity> exceptionHandler(ConstraintViolationException exception) {
        logger.error("ResponseException exception: {}", ExceptionUtils.getStackTrace(exception));
        return new ResponseEntity<>(new ErrorEntity(ErrorCode.PARAMETER_ERROR,
                ErrorCode.PARAMETER_ERROR.getMessage(),
                null, null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorEntity> exceptionHandler(MethodArgumentNotValidException exception) {
        logger.error("ResponseException exception: {}", ExceptionUtils.getStackTrace(exception));
        return new ResponseEntity<>(new ErrorEntity(ErrorCode.PARAMETER_ERROR, exception.getMessage(),
                null, ExceptionUtils.getStackTrace(exception)),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BaseResponseException.class)
    public ResponseEntity<ErrorEntity> exceptionHandler(BaseResponseException exception) {
        logger.warn("ResponseException with status: [{}], errorCode: [{}]", exception.getStatus(),
                exception.getErrorCode());
        logger.warn(ExceptionUtils.getStackTrace(exception));
        return new ResponseEntity<>(new ErrorEntity(exception.getErrorCode(),
                exception.getErrorCode().getMessage(),
                exception.getErrorMessages(),
                exception.getStackTraceInfo()),
                exception.getStatus());
    }

}
