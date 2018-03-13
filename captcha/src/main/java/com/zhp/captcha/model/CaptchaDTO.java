package com.zhp.captcha.model;

/**
 * Created by zhouhh2 on 2017/1/4.
 */
public class CaptchaDTO {
    private String capTextCode;
    private String capImage;

    public CaptchaDTO() {
    }

    public CaptchaDTO(String capTextCode, String capImage) {
        this.capTextCode = capTextCode;
        this.capImage = capImage;
    }

    public String getCapTextCode() {
        return capTextCode;
    }

    public void setCapTextCode(String capTextCode) {
        this.capTextCode = capTextCode;
    }

    public String getCapImage() {
        return capImage;
    }

    public void setCapImage(String capImage) {
        this.capImage = capImage;
    }
}
