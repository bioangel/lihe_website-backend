package com.zhp.cache.base;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * Created by zhouhh2 on 2016/12/27.
 */
public class CustomKey implements Serializable {
    private final String className;
    private final String methodName;
    private final Object[] params;

    public CustomKey(String className, String methodName, Object... elements) {
        Assert.notNull(elements, "Elements must not be null");
        this.params = new Object[elements.length];
        this.className = className;
        this.methodName = methodName;
        System.arraycopy(elements, 0, this.params, 0, elements.length);
    }

    public CustomKey() {
        this.className = "";
        this.methodName = "";
        this.params = null;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(className);
        stringBuilder.append("_");
        stringBuilder.append(methodName);
        stringBuilder.append("_");
        stringBuilder.append(StringUtils.arrayToCommaDelimitedString(params));
        return stringBuilder.toString();
    }

}
