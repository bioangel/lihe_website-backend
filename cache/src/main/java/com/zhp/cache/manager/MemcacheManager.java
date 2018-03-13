package com.zhp.cache.manager;

import com.zhp.cache.base.CacheConstants;
import com.zhp.cache.service.MemcachedService;
import net.spy.memcached.MemcachedClient;
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
 * Created by zhouhh2 on 2016/12/26.
 */
@Configuration
@Import({MemcachedService.class})
@ConditionalOnExpression("${" + CacheConstants.CACHE_ENABLED + ":true} && '${"
        + CacheConstants.CACHE_NAME + "}' == '" + CacheConstants.CACHE_MEMCACHE + "'")
public class MemcacheManager extends AbstractCacheManager {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MemcachedClient memcachedClient;

    @Autowired
    private MemcachedService memcachedCache;

    @Override
    protected Collection<? extends Cache> loadCaches() {
        return new ArrayList<Cache>(Arrays.asList(memcachedCache));
    }

    @Override
    public void afterPropertiesSet() {
    }

    @Override
    protected Cache getMissingCache(String name) {
        if (!this.memcachedCache.getName().equalsIgnoreCase(name)) {
            return null;
        }
        logger.info("create cache.......{}", name);
        memcachedCache.setClient(memcachedClient);
        return memcachedCache;
    }
}
