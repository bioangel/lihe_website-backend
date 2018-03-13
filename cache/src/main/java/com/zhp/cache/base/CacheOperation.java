package com.zhp.cache.base;

import java.util.List;
import java.util.Set;

/**
 * Created by zhouhh2 on 2016/12/22.
 */
public interface CacheOperation {
    public <T> T get(Object key, Class<T> type);

    public void delete(Object key);

    public void save(Object key, Object value, int timeoutSeconds);

    public <T> T getAndTouch(Object key, int timeoutSeconds, Class<T> type);

    public void touch(Object key, int timeoutSeconds);

    public <T> T getPattern(Object key, Class<T> type);

    public <T> List<T> getMultiKeys(Set keys);

    public Long increment(Object key, Object hashKey, final long delta);

    public Long increment(Object key, Object hashKey, final long delta, int seconds);
}
