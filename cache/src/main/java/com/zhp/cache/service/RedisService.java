package com.zhp.cache.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhp.cache.base.CacheConstants;
import com.zhp.cache.base.CacheOperation;
import com.zhp.cache.config.RedisConfig;
import com.zhp.common.exception.Assert;
import com.zhp.common.exception.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@Service
@ConditionalOnExpression("${" + CacheConstants.CACHE_ENABLED + "} && '${"
        + CacheConstants.CACHE_NAME + "}' == '" + CacheConstants.CACHE_REDIS + "'")
@Import({ObjectMapper.class, RedisConfig.class})
public class RedisService implements Cache, CacheOperation {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private String name = CacheConstants.CACHE_PREFIX;

    @Value("${app.name}")
    private String appName;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RedisConfig redisConfig;

    @Autowired
    private ValueOperations<String, Object> valueOperations;

    @Autowired
    private RedisTemplate redisTemplate;

    private String getKey(Object key) {
        return name + "_" + appName + "_" + String.valueOf(key);
    }

    private void put(String key, Object value) {
        put(key, value, redisConfig.getExpireTime());
    }

    private void put(String key, Object value, long timeoutSeconds) {
        valueOperations.set(key, value, timeoutSeconds, TimeUnit.SECONDS);
        logger.info("cached to redis, key: [{}], expired seconds: [{}]", key, timeoutSeconds);
    }

    public String get(String key) {
        Assert.isTrue(StringUtils.isNotBlank(key), ErrorCode.REDIS_KEY_NOT_NULL);
        logger.debug("get string value from redis, key: [{}]", key);
        Object obj = valueOperations.get(getKey(key));
        return obj == null ? null : obj.toString();
    }

    public void delete(String key) {
        Assert.isTrue(StringUtils.isNotBlank(key), ErrorCode.REDIS_KEY_NOT_NULL);
        valueOperations.getOperations().delete(key);
        logger.info("delete from redis, key: [{}]", key);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getNativeCache() {
        return valueOperations;
    }

    @Override
    public ValueWrapper get(Object key) {
        logger.info("Getting value from cache key: [{}]", getKey(key));
        Object value = valueOperations.get(getKey(key));
        return value != null ? new SimpleValueWrapper(value) : null;
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        logger.info("Getting value by type [{}] from cache key: [{}]", type.getName(), getKey(key));
        Object value = valueOperations.get(getKey(key));
        return type.cast(value);
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        return null;
    }

    @Override
    public void put(Object key, Object value) {
        put(getKey(key), value);
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        return null;
    }

    @Override
    public void evict(Object key) {
        delete(getKey(key));
    }

    @Override
    public void clear() {

    }

    @Override
    public void delete(Object key) {
        delete(getKey(key));
    }

    @Override
    public void save(Object key, Object value, int timeoutSeconds) {
        put(getKey(key), value, timeoutSeconds);
    }

    @Override
    public <T> T getAndTouch(Object key, int timeoutSeconds, Class<T> type) {
        return null;
    }

    @Override
    public void touch(Object key, int timeoutSeconds) {
        redisTemplate.expire(getKey(key), timeoutSeconds, TimeUnit.SECONDS);
    }

    @Override
    public <T> T getPattern(Object key, Class<T> type) {
        logger.info("Getting value by type [{}] from cache key: [{}]", type.getName(), getKey(key));
        Object value = redisTemplate.keys(getKey(key));
        return type.cast(value);
    }

    @Override
    public <T> List<T> getMultiKeys(Set keys) {
        List<Object> keySet = new ArrayList();
        for (Object key : keys) {
            keySet.add(getKey(key));
        }
        List<T> value = redisTemplate.opsForValue().multiGet(keySet);
        return value;
    }

    @Override
    public Long increment(Object key, Object hashKey, long delta) {
        return redisTemplate.opsForHash().increment(getKey(key), hashKey, delta);
    }

    @Override
    public Long increment(Object key, Object hashKey, long delta, int seconds) {
        Long result = increment(key, hashKey, delta);
        Long time = redisTemplate.getExpire(getKey(key), TimeUnit.SECONDS);
        if (time == -1 || time == -2 || time == 0) {
            redisTemplate.expire(getKey(key), seconds, TimeUnit.SECONDS);
        }
        return result;
    }
}
