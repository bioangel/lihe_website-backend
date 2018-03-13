package com.zhp.db.aspect;

import com.zhp.db.base.Constants;
import com.zhp.db.config.DataSourceConfigRegister;
import com.zhp.db.config.DynamicDataSourceContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by zhouhh2 on 2016/8/24.
 */

@Aspect
@Component
@Order(-1)
@ConditionalOnProperty(name = Constants.flag_multipleDs, havingValue = "true")
public class DynamicDataSourceMethodAspect {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final String aspectStr = "execution(* com.lenovo..dbservice.*Service.*(..))";

    @Autowired
    private DataSourceConfigRegister dataSourceConfigRegister;

    @Before(aspectStr)
    public void changeDataSource(JoinPoint point) {
        Thread current = Thread.currentThread();
        String method = point.getSignature().getName();
        String ds = null;

        if (DynamicDataSourceContextHolder.getDataSourceType() == null) {
            if (this.isReadMethod(method)) {
                ds = dataSourceConfigRegister.getCustomReadDataSource();
            }
            DynamicDataSourceContextHolder.setDataSourceType(Optional.ofNullable(ds).orElse(Constants.defaultDs));
            logger.info("Datasource set to [{}] > [{}], Thread name: [{}]",
                    DynamicDataSourceContextHolder.getDataSourceType(), point.getSignature(), current.getName());
        }
    }

    private boolean isReadMethod(String method) {
        for (String check : Constants.READ_CHECK) {
            if (method.indexOf(check) == 0) {
                return true;
            }
        }
        return false;
    }

    @After(aspectStr)
    public void restoreDataSource(JoinPoint point) {
        if (DynamicDataSourceContextHolder.getDataSourceType() == null) {
            return;
        }
        Thread current = Thread.currentThread();
        DynamicDataSourceContextHolder.clearDataSourceType();
        logger.info("Thread datasource is clear. Execute method: {}, Thread name: {}",
                point.getSignature(), current.getName());
    }
}
