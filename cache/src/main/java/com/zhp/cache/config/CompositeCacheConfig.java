package com.zhp.cache.config;

import com.zhp.cache.base.CacheConstants;
import com.zhp.cache.manager.MemcacheManager;
import com.zhp.cache.manager.RedisManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by zhouhh2 on 2016/12/27.
 */
@Configuration
@ConditionalOnProperty({CacheConstants.CACHE_ENABLED})
@Import({MemcacheManager.class, RedisManager.class})
public class CompositeCacheConfig {

    @Autowired(required = false)
    private MemcacheManager memcacheManager;

    @Autowired(required = false)
    private RedisManager redisManager;

    @Bean
    @Primary
    public CompositeCacheManager compositeCacheManager() {
        CompositeCacheManager compositeCacheManager = new CompositeCacheManager();
        compositeCacheManager.setFallbackToNoOpCache(true);
        compositeCacheManager.setCacheManagers(getCacheManagers());
        return compositeCacheManager;
    }

    private List<CacheManager> getCacheManagers() {
        List<CacheManager> cacheManagerList = new ArrayList<>();
        Optional.ofNullable(memcacheManager).ifPresent(e -> cacheManagerList.add(e));
        Optional.ofNullable(redisManager).ifPresent(e -> cacheManagerList.add(e));
        return cacheManagerList;
    }
}
