package com.zhp.security.utils;

/**
 * Created by zhh on 16/12/18.
 */

import com.zhp.security.config.SecurityConfig;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
@Import({SecurityConfig.class})
public class SecurityUtils {

    @Autowired
    private StandardPBEStringEncryptor encryptor;

    @Autowired
    private SecurityConfig securityConfig;

    public String encrypt(String str) {
        return encryptor.encrypt(str);
    }

    public String decrypt(String str) {
        return encryptor.decrypt(str);
    }

    public boolean isEncryptPasswd() {
        return securityConfig.isPassword();
    }

    public boolean getAd() {
        return securityConfig.isAd();
    }

    public boolean isAuthority() {
        return securityConfig.isAuthority();
    }
}
