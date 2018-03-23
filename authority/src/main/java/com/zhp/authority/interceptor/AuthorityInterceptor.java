package com.zhp.authority.interceptor;

import com.zhp.cache.base.CacheConstants;
import com.zhp.cache.base.CacheOperation;
import com.zhp.sys.base.AccessConstants;
import com.zhp.authority.dbservice.AuthorityService;
import com.zhp.common.exception.BadRequestException;
import com.zhp.common.exception.ErrorCode;
import com.zhp.security.utils.SecurityUtils;
import com.zhp.sys.model.LoginVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by zhouhh2 on 2016/7/2.
 */
@Configuration
public class AuthorityInterceptor extends WebMvcConfigurerAdapter {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CacheOperation cacheOperation;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private SecurityUtils securityUtils;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getHandlerInterceptorAdapter())
                .addPathPatterns("/**")
                .excludePathPatterns("/account/login",
                                     "/error/**",
                                     "/captcha");
        super.addInterceptors(registry);
    }

    private HandlerInterceptorAdapter getHandlerInterceptorAdapter() {
        return new HandlerInterceptorAdapter() {
            @Override
            @ExceptionHandler
            public boolean preHandle(HttpServletRequest request,
                                     HttpServletResponse response, Object handler) throws Exception {
                if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                    return true;
                }
                if (!securityUtils.isAuthority()) {
                    return true;
                }
                String token = request.getHeader(AccessConstants.TOKEN);
                logger.debug("Access uri [{}]", request.getRequestURI());
                checkToken(token);
                checkAuthority(request, token);
                return true;
            }
        };
    }

    private void checkToken(String token) throws IOException {
        if (token == null || token.trim().equals("")) {
            throw new BadRequestException(ErrorCode.TOKEN_IS_MISSING);
        }
        LoginVO tokenRef = cacheOperation.get(token, LoginVO.class);
        if (null == tokenRef) {
            throw new BadRequestException(ErrorCode.INVALID_TOKEN);
        }
    }

    private void checkAuthority(HttpServletRequest request, String token) throws IOException {
        String uri = request.getRequestURI();
        Map<String, List<String>> uidApi = getUidApi();
        LoginVO tokenRef = cacheOperation.get(token, LoginVO.class);

        if (uidApi == null || null == uidApi.get(tokenRef.getUid())) {
            throw new BadRequestException(ErrorCode.INVALID_USER);
        }

        logger.debug(uidApi.get(tokenRef.getUid()).toString());
        if (!StringUtils.containsAny(uri, uidApi.get(tokenRef.getUid())
                .toArray(new String[uidApi.get(tokenRef.getUid()).size()]))) {
            throw new BadRequestException(ErrorCode.NOT_PERMITTED);
        }
        cacheOperation.touch(token, CacheConstants.EXPIRE_TIME);
    }

    private Map<String, List<String>> getUidApi() {
        Map<String, List<String>> uidApi = cacheOperation.get(CacheConstants.UID_API, Map.class);

        if (uidApi == null || uidApi.size() <= 0) {
            uidApi = authorityService.getUidApi();
            cacheOperation.save(CacheConstants.UID_API, uidApi, CacheConstants.EXPIRE_TIME);
        }
        return uidApi;
    }

}
