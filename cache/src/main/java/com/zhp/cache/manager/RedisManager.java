package com.zhp.cache.manager;

import com.zhp.cache.base.CacheConstants;
import com.zhp.cache.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by zhouhh2 on 2016/12/28.
 */
@Configuration
@ConditionalOnExpression("${" + CacheConstants.CACHE_ENABLED + "} && '${"
        + CacheConstants.CACHE_NAME + "}' == '" + CacheConstants.CACHE_REDIS + "'")
@Import({RedisService.class})
public class RedisManager extends AbstractCacheManager {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisService redisCache;

    @Override
    protected Collection<? extends Cache> loadCaches() {
        return new ArrayList<Cache>(Arrays.asList(redisCache));
    }

    @Override
    protected Cache getMissingCache(String name) {
        if (!this.redisCache.getName().equalsIgnoreCase(name)) {
            return null;
        }
        logger.info("create cache.......{}", name);
        return redisCache;
    }

    @Override
    public void afterPropertiesSet() {
    }
}
