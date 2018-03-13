package com.zhp.common.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    // * 0 ~ 1000, system error
    UNKNOWN(0, HttpStatus.BAD_REQUEST, "系统未知错误"),
    SYSTEM_ERROR(1, HttpStatus.INTERNAL_SERVER_ERROR, "系统错误"),
    NOT_PERMITTED(3, HttpStatus.FORBIDDEN, "不允许访问"),
    CREATE_OBJECT_ERROR(10001, HttpStatus.BAD_REQUEST, ""),
    TOKEN_IS_MISSING(10003, HttpStatus.BAD_REQUEST, "未找到TOKEN"),
    INVALID_TOKEN(10004, HttpStatus.BAD_REQUEST, "无效的TOKEN"),
    PARAMETER_ERROR(10006, HttpStatus.BAD_REQUEST, "请求参数错误"),
    UPLOADFILE_ERROR(10007, HttpStatus.BAD_REQUEST, "upload参数不能为空"),
    IO_ERROR(10008, HttpStatus.BAD_REQUEST, "IO错误"),
    UPLOADFILE_IO_ERROR(10009, HttpStatus.BAD_REQUEST, "文件上传发生IO错误"),
    UPLOADFILE_TYPE_ERROR(10010, HttpStatus.BAD_REQUEST, "文件上传类型错误"),
    DATETIME_ERROR(10011, HttpStatus.BAD_REQUEST, "日期转换错误"),

    USER_NOT_FOUND(30001, HttpStatus.BAD_REQUEST, "未找到用户"),
    USERNAME_OR_PASSWORD_ERROR(30002, HttpStatus.BAD_REQUEST, "用户名密码错误"),
    INVALID_CAPTCHA(30003, HttpStatus.BAD_REQUEST, "验证码错误"),
    INVALID_USER(30007, HttpStatus.BAD_REQUEST, "无效的用户ID"),
    CREATE_USER_ERROR(30011, HttpStatus.BAD_REQUEST, "创建用户失败"),

    REDIS_KEY_VALUE_NOT_NULL(90001, HttpStatus.BAD_REQUEST, "键或值不能为空"),
    REDIS_KEY_NOT_NULL(90002, HttpStatus.BAD_REQUEST, "键不能为空"),

    JSON_PARSE_TO_OBJECT_ERROR(70004, HttpStatus.BAD_REQUEST, "json对象化错误"),
    PARSE_TO_JSON_STRING_ERROR(70005, HttpStatus.BAD_REQUEST, "对象转化字符串错误");

    private final int code;

    private final HttpStatus status;

    private final String message;

    ErrorCode(int code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ErrorCode{"
                + "code=" + code
                + ", status=" + status
                + ", message='" + message + '\''
                + '}';
    }
}
