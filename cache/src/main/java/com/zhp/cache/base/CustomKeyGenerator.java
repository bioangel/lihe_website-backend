package com.zhp.cache.base;

import com.zhp.common.utils.CommonUtils;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

/**
 * Created by zhouhh2 on 2016/12/27.
 */
public class CustomKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        if (params.length == 0) {
            return CommonUtils.getClassName(target.getClass()) + "_" + method.getName();
        }
        return new CustomKey(CommonUtils.getClassName(target.getClass()), method.getName(), params);
    }
}

