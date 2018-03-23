package com.zhp.sys.base;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhouhh2 on 2016/12/31.
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = AccessConstants.ACCESS_PREFIX)
public class AccessConfig {
    private String header;
    private String method;
    private int maxAge;
    private String origin;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
