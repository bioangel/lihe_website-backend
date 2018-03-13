package com.zhp.captcha.cache;

/**
 * Created by dtong on 3/25/16.
 */
public interface CaptchaCache {
    void push(String token, String text);

    String find(String key);
}
