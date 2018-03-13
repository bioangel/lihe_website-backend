package com.zhp.captcha.cache;

import com.zhp.cache.base.CacheOperation;
import com.zhp.captcha.base.CaptchaConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ExternalCache implements CaptchaCache {
    private static final int CAPTCHA_VALID_DURATION_IN_MINUTES = 5;

    @Autowired
    private CacheOperation cacheOperation;

    @Override
    public void push(String token, String text) {
        cacheOperation.save(token + "_" + CaptchaConstants.CAPTCHA_PREFIX, text, CAPTCHA_VALID_DURATION_IN_MINUTES * 60);
    }

    @Override
    public String find(String key) {
        String checkResult = cacheOperation.get(
                key + "_" + CaptchaConstants.CAPTCHA_PREFIX, String.class);
        if (checkResult != null) {
            cacheOperation.delete(key + "_" + CaptchaConstants.CAPTCHA_PREFIX);
        }
        return Optional.ofNullable(checkResult).orElse(null);
    }
}
