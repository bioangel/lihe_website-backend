package com.zhp.cache.config;

import com.zhp.cache.base.CacheConstants;
import com.zhp.cache.base.CacheManagerConfig;
import net.spy.memcached.*;
import net.spy.memcached.spring.MemcachedClientFactoryBean;
import net.spy.memcached.transcoders.SerializingTranscoder;
import net.spy.memcached.transcoders.Transcoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by zhouhh2 on 2016/12/20.
 */
@Configuration
@ConditionalOnExpression("${" + CacheConstants.CACHE_ENABLED + "} && '${"
        + CacheConstants.CACHE_NAME + "}' == '" + CacheConstants.CACHE_MEMCACHE + "'")
@EnableConfigurationProperties
@ConfigurationProperties(prefix = CacheConstants.CACHE_MEMCACHE)
@Import({CacheManagerConfig.class})
public class MemcacheConfig {
    private String servers;
    private int compressionThreshold;
    private long opTimeout;
    private int timeoutExceptionThreshold;
    private boolean useNagleAlgorithm;

    private int expireTime;

    @Bean
    public MemcachedClientFactoryBean memcachedClient() throws Exception {
        MemcachedClientFactoryBean memcachedClientFactoryBean = new MemcachedClientFactoryBean();
        memcachedClientFactoryBean.setServers(servers);
        memcachedClientFactoryBean.setProtocol(ConnectionFactoryBuilder.Protocol.BINARY);
        memcachedClientFactoryBean.setTranscoder(transcoder());
        memcachedClientFactoryBean.setOpTimeout(opTimeout);
        memcachedClientFactoryBean.setTimeoutExceptionThreshold(timeoutExceptionThreshold);
        memcachedClientFactoryBean.setHashAlg(DefaultHashAlgorithm.KETAMA_HASH);
        memcachedClientFactoryBean.setLocatorType(ConnectionFactoryBuilder.Locator.CONSISTENT);
        memcachedClientFactoryBean.setFailureMode(FailureMode.Redistribute);
        memcachedClientFactoryBean.setUseNagleAlgorithm(useNagleAlgorithm);
        return memcachedClientFactoryBean;
    }

    private Transcoder transcoder() {
        SerializingTranscoder transcoder = new SerializingTranscoder();
        transcoder.setCompressionThreshold(compressionThreshold);
        return transcoder;
    }

    public String getServers() {
        return servers;
    }

    public void setServers(String servers) {
        this.servers = servers;
    }

    public int getCompressionThreshold() {
        return compressionThreshold;
    }

    public void setCompressionThreshold(int compressionThreshold) {
        this.compressionThreshold = compressionThreshold;
    }

    public long getOpTimeout() {
        return opTimeout;
    }

    public void setOpTimeout(long opTimeout) {
        this.opTimeout = opTimeout;
    }

    public int getTimeoutExceptionThreshold() {
        return timeoutExceptionThreshold;
    }

    public void setTimeoutExceptionThreshold(int timeoutExceptionThreshold) {
        this.timeoutExceptionThreshold = timeoutExceptionThreshold;
    }

    public boolean isUseNagleAlgorithm() {
        return useNagleAlgorithm;
    }

    public void setUseNagleAlgorithm(boolean useNagleAlgorithm) {
        this.useNagleAlgorithm = useNagleAlgorithm;
    }

    public int getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }
}
