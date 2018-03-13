package com.zhp.cache.service;

import com.zhp.cache.base.CacheConstants;
import com.zhp.cache.base.CacheOperation;
import com.zhp.cache.config.MemcacheConfig;
import net.spy.memcached.CASValue;
import net.spy.memcached.MemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * Created by zhouhh2 on 2016/12/26.
 */
@Service
@ConditionalOnExpression("${" + CacheConstants.CACHE_ENABLED + ":true} && '${"
        + CacheConstants.CACHE_NAME + "}' == '" + CacheConstants.CACHE_MEMCACHE + "'")
@Import({MemcacheConfig.class})
public class MemcachedService implements Cache, CacheOperation {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MemcacheConfig memcacheConfig;

    private String name = CacheConstants.CACHE_PREFIX;

    @Value("${app.name}")
    private String appName;

    private MemcachedClient client;

    public void setClient(MemcachedClient client) {
        this.client = client;
    }

    public MemcachedService(String name, MemcachedClient client) {
        this.name = name;
        this.client = client;
    }

    public MemcachedService() {
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return this.client;
    }

    @Override
    public ValueWrapper get(Object key) {
        logger.info("Getting value from cache key: [{}]", getKey(key));
        Object value = client.get(getKey(key));
        return value != null ? new SimpleValueWrapper(value) : null;
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        logger.info("Getting value by type [{}] from cache key: [{}]", type.getName(), getKey(key));
        Object value = client.get(getKey(key));
        return type.cast(value);
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        return null;
    }

    @Override
    public void put(Object key, Object value) {
        save(key, value, memcacheConfig.getExpireTime());
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        return null;
    }

    @Override
    public void evict(Object key) {
        logger.info("Evicting cache for key: [{}]", getKey(key));
        client.delete(getKey(key));
    }

    @Override
    public void clear() {

    }

    private String getKey(Object key) {
        return name + "_" + appName + "_" + String.valueOf(key);
    }

    @Override
    public void delete(Object key) {
        evict(key);
    }

    @Override
    public void save(Object key, Object value, int timeoutSeconds) {
        client.set(getKey(key), timeoutSeconds, value);
        logger.info("Cached to memcached, key: [{}], expired seconds: [{}]", getKey(key), timeoutSeconds);
    }

    @Override
    public <T> T getAndTouch(Object key, int timeoutSeconds, Class<T> type) {
        logger.info("Getting and touch value from cache key: [{}]", getKey(key));
        CASValue<Object> value = client.getAndTouch(getKey(key), timeoutSeconds);
        return type.cast(value.getValue());
    }

    @Override
    public void touch(Object key, int timeoutSeconds) {
        client.touch(key.toString(), timeoutSeconds);
    }

    @Override
    public <T> T getPattern(Object key, Class<T> type) {
        return null;
    }

    @Override
    public <T> List<T> getMultiKeys(Set keys) {
        return null;
    }

    @Override
    @Deprecated
    public Long increment(Object key, Object hashKey, long delta) {
        return null;
    }

    @Override
    @Deprecated
    public Long increment(Object key, Object hashKey, long delta, int seconds) {
        return null;
    }
}
