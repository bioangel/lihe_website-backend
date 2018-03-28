package com.zhp.sys.interceptor;

import com.zhp.cache.base.CacheOperation;
import com.zhp.common.exception.BadRequestException;
import com.zhp.sys.base.AccessConstants;
import com.zhp.sys.base.SystemLog;
import com.zhp.sys.dbservice.SysLogService;
import com.zhp.sys.model.LoginVO;
import com.zhp.sys.model.SysLog;
import com.zhp.sys.model.UserLoginDTO;
import com.zhp.sys.utils.SysUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/**
 * Created by zhouhh2 on 2018/3/22.
 */

@Aspect
@Component
public class SystemLogAspect {
    private static final ThreadLocal<Long> beginTime = new ThreadLocal();

    private static final ThreadLocal<SysLog> sysLog = new ThreadLocal();

    private static final ThreadLocal<SystemLog> systemLogThreadLocal = new ThreadLocal();

    @Autowired
    private CacheOperation cacheOperation;

    @Autowired
    private SysLogService sysLogService;

    @Pointcut("@annotation(com.zhp.sys.base.SystemLog)")
    private void controllerAspect() {

    }

    private void init() {
        beginTime.set(new Date().getTime());
        sysLog.set(new SysLog(new Date()));
    }

    @After("controllerAspect()")
    public void after() {
        sysLog.get().setActionCostTime(new Date().getTime() - beginTime.get());
    }

    @AfterReturning("controllerAspect()")
    public void doAfter() {
    }

    @AfterThrowing(value = "controllerAspect()", throwing = "exception")
    public void doAfterThrow(Exception exception) {
        sysLog.get().setActionCostTime(new Date().getTime() - beginTime.get());
        sysLog.get().setActionStatus("FAILED");
        sysLog.get().setActionStatusDesc(exception.getMessage());
        parseException(exception);
        sysLogService.asyncSaveLog(sysLog.get());
    }

    private void parseException(Exception exception) {
        if (exception instanceof BadRequestException) {
            sysLog.get().setBusinessStatus(((BadRequestException) exception).getErrorCode().getStatus().toString());
            sysLog.get().setBusinessStatusDesc(((BadRequestException) exception).getErrorCode().getMessage());
        } else {
            sysLog.get().setBusinessStatus("500");
        }
    }

    @Around("controllerAspect()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        init();
        Object target = pjp.getTarget();
        String methodName = pjp.getSignature().getName();
        Class[] parameterTypes = ((MethodSignature) pjp.getSignature()).getMethod().getParameterTypes();
        Method method = target.getClass().getMethod(methodName, parameterTypes);
        Object[] args = pjp.getArgs();
        systemLogThreadLocal.set(method.getAnnotation(SystemLog.class));

        parseRequest(args);
        ResponseEntity result = (ResponseEntity) pjp.proceed();
        afterProceed(result);
        sysLogService.asyncSaveLog(sysLog.get());
        return result;
    }

    private void afterProceed(ResponseEntity result) {
        sysLog.get().setActionStatus("SUCCESS");
        sysLog.get().setBusinessStatus(result.getStatusCode().toString());
        if (systemLogThreadLocal.get().isGetReturn()) {
            sysLog.get().setBusinessStatusDesc(result.getBody().toString());
        }
        if (systemLogThreadLocal.get().action().equalsIgnoreCase(AccessConstants.LOGIN_ACTION)) {
            sysLog.get().setToken(((Map)result.getBody()).get(AccessConstants.TOKEN).toString());
        }
    }

    private void parseRequest(Object[] args) {
        sysLog.get().setAction(systemLogThreadLocal.get().action());
        sysLog.get().setActionGroup(systemLogThreadLocal.get().group());
        if (systemLogThreadLocal.get().action().equalsIgnoreCase(AccessConstants.LOGIN_ACTION)) {
            parseLogin(args);
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        sysLog.get().setIp(SysUtils.getIp(request));
        String token = request.getHeader(AccessConstants.TOKEN);
        if (StringUtils.isBlank(token)) {
            return;
        }
        LoginVO tokenRef = cacheOperation.get(token, LoginVO.class);
        if (tokenRef != null) {
            sysLog.get().setUid(tokenRef.getUname());
            sysLog.get().setToken(token);
        }
    }

    private void parseLogin(Object[] args) {
        if (args == null || args.length == 0) {
            return;
        }
        UserLoginDTO user = (UserLoginDTO) args[0];
        sysLog.get().setUid(user.getUsername());
    }
}
