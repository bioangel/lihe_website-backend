package com.zhp.sys.interceptor;

import com.zhp.cache.base.CacheOperation;
import com.zhp.sys.base.AccessConstants;
import com.zhp.sys.base.SystemLog;
import com.zhp.sys.dbservice.SysLogService;
import com.zhp.sys.model.LoginVO;
import com.zhp.sys.model.SysLog;
import com.zhp.sys.utils.SysUtils;
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

/**
 * Created by zhouhh2 on 2018/3/22.
 */

@Aspect
@Component
public class SystemLogAspect {
    private static final ThreadLocal<Long> beginTime = new ThreadLocal();

    private static final ThreadLocal<SysLog> sysLog = new ThreadLocal();

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
        sysLogService.asyncSaveLog(sysLog.get());
    }

    @Around("controllerAspect()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        init();
        Object target = pjp.getTarget();
        String methodName = pjp.getSignature().getName();
        Class[] parameterTypes = ((MethodSignature)pjp.getSignature()).getMethod().getParameterTypes();
        Method method = target.getClass().getMethod(methodName, parameterTypes);
        SystemLog systemlog = method.getAnnotation(SystemLog.class);
        sysLog.get().setAction(systemlog.action());
        sysLog.get().setActionGroup(systemlog.group());

        parseRequest();
        ResponseEntity result = (ResponseEntity) pjp.proceed();
        sysLog.get().setActionStatus("SUCCESS");
        sysLog.get().setBusinessStatus(result.getStatusCode().toString());
        if (systemlog.isGetReturn()) {
            sysLog.get().setBusinessStatusDesc(result.getBody().toString());
        }
        sysLogService.asyncSaveLog(sysLog.get());
        return result;
    }

    private void parseRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader(AccessConstants.TOKEN);
        LoginVO tokenRef = cacheOperation.get(token, LoginVO.class);
        if (tokenRef != null) {
            sysLog.get().setUid(tokenRef.getUid());
        }
        sysLog.get().setIp(SysUtils.getIp(request));
    }
}
