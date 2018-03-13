package com.zhp.cache.base;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhouhh2 on 2016/12/26.
 */

@Configuration
@ConditionalOnProperty({CacheConstants.CACHE_ENABLED})
@EnableCaching(proxyTargetClass = true)
public class CacheManagerConfig extends CachingConfigurerSupport {

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new CustomKeyGenerator();
    }

}
