package com.zhp.captcha.service;

import com.google.code.kaptcha.Producer;
import com.zhp.captcha.base.CaptchaConstants;
import com.zhp.captcha.config.CaptchaConfig;
import com.zhp.captcha.model.CaptchaDTO;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by zhouhh2 on 2017/1/4.
 */

@Service
@ConditionalOnProperty({CaptchaConstants.CAPTCHA_ENABLE})
@Import({CaptchaConfig.class})
public class CaptchaService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Producer captchaProducer;

    public CaptchaDTO getCaptcha() {
        String codeStr = captchaProducer.createText();
        return new CaptchaDTO(codeStr, buildImageStr(codeStr));
    }

    private String buildImageStr(String codeStr) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BufferedImage bi = captchaProducer.createImage(codeStr);
        try {
            ImageIO.write(bi, "jpg", out);
        } catch (IOException e) {
            logger.error(ExceptionUtils.getStackTrace(e));
            return null;
        }
        return Base64Utils.encodeToString(out.toByteArray());
    }
}
