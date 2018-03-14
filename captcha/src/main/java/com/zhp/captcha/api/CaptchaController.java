package com.zhp.captcha.api;

import com.zhp.captcha.base.CaptchaConstants;
import com.zhp.captcha.cache.ExternalCache;
import com.zhp.captcha.model.CaptchaDTO;
import com.zhp.captcha.service.CaptchaService;
import com.zhp.common.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@ConditionalOnProperty({CaptchaConstants.CAPTCHA_ENABLE})
public class CaptchaController {
    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private ExternalCache externalCache;

    @RequestMapping(method = {RequestMethod.GET}, value = "/captcha")
    public Map captcha() throws ServletException, IOException {
        String authToken = CommonUtils.getuuid();
        CaptchaDTO captchaDTO = captchaService.getCaptcha();
        Map<String, String> captchaMap = new HashMap<>();
        captchaMap.put("code", authToken);
        captchaMap.put("jpgBase64", captchaDTO.getCapImage());
        externalCache.push(authToken, captchaDTO.getCapTextCode());
        return captchaMap;
    }
}
