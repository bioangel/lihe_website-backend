package com.zhp.captcha.config;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;
import com.zhp.captcha.base.CaptchaConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Created by zhouhh2 on 2017/1/4.
 */

@Configuration
@ConditionalOnProperty({CaptchaConstants.CAPTCHA_ENABLE})
@ConfigurationProperties(prefix = CaptchaConstants.CAPTCHA_PREFIX)
@EnableConfigurationProperties
public class CaptchaConfig {
    private boolean enabled;
    private String border;
    private String charString;
    private int charLength;
    private int charSpace;

    @Bean
    public Producer captchaProducer() {
        Config config = new Config(buildCaptchaProp());
        return config.getProducerImpl();
    }

    private Properties buildCaptchaProp() {
        Properties props = new Properties();
        props.put(Constants.KAPTCHA_BORDER, border);
        props.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING, charString);
        props.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, charLength);
        props.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE, charSpace);
        props.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "0,0,255");
        return props;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public String getCharString() {
        return charString;
    }

    public void setCharString(String charString) {
        this.charString = charString;
    }

    public int getCharLength() {
        return charLength;
    }

    public void setCharLength(int charLength) {
        this.charLength = charLength;
    }

    public int getCharSpace() {
        return charSpace;
    }

    public void setCharSpace(int charSpace) {
        this.charSpace = charSpace;
    }
}
