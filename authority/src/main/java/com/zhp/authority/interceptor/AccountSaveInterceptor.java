package com.zhp.authority.interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by zhouhh2 on 2017/3/15.
 */

@Aspect
@Component
public class AccountSaveInterceptor {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final String aspectStr =
            "execution(* com.zhp..authority.dbservice.AccountService.save(..))";

    @Before(aspectStr)
    public void beforeSaveAccount(JoinPoint point) {

    }

    @After(aspectStr)
    public void afterSaveAccount(JoinPoint point) {

    }
}
