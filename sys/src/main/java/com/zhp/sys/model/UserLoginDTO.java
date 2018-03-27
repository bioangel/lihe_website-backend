package com.zhp.sys.model;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by zhouhh2 on 2016/10/25.
 */
public class UserLoginDTO {
    @NotBlank(message = "{NotNull.username}")
    private String username;
    private String password;
    private int time;
    private int ad;
    private String verifyCode;
    private String verifyToken;

    public UserLoginDTO() {
    }

    public UserLoginDTO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getAd() {
        return ad;
    }

    public void setAd(int ad) {
        this.ad = ad;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getVerifyToken() {
        return verifyToken;
    }

    public void setVerifyToken(String verifyToken) {
        this.verifyToken = verifyToken;
    }
}
