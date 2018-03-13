package com.zhp.db.aspect;

import com.zhp.db.annotation.TargetDataSource;
import com.zhp.db.base.Constants;
import com.zhp.db.config.DynamicDataSourceContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(-1)
@Component
@ConditionalOnProperty(name = Constants.flag_multipleDs, havingValue = "true")
public class DynamicDataSourceAspect {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    @Before("@annotation(ds)")
    public void changeDataSource(JoinPoint point, TargetDataSource ds) throws Throwable {
        Thread current = Thread.currentThread();
        String dsId = ds.name();
        if (!DynamicDataSourceContextHolder.containsDataSource(dsId)) {
            logger.error("Datasource [{}] not exists，use default datasource. Method [{}]",
                    ds.name(), point.getSignature());
            DynamicDataSourceContextHolder.setDataSourceType(Constants.defaultDs);
        } else {
            DynamicDataSourceContextHolder.setDataSourceType(ds.name());
        }
        logger.info("Datasource set to [{}] > [{}], Thread name: [{}]",
                DynamicDataSourceContextHolder.getDataSourceType(), point.getSignature(), current.getName());
    }

    @After("@annotation(ds)")
    public void restoreDataSource(JoinPoint point, TargetDataSource ds) {
        if (DynamicDataSourceContextHolder.getDataSourceType() == null) {
            return;
        }
        Thread current = Thread.currentThread();
        DynamicDataSourceContextHolder.clearDataSourceType();
        logger.info("Thread datasource is clear. Execute method: {}, Thread name: {}",
                point.getSignature(), current.getName());
    }
}
