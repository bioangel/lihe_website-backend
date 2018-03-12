package com.lihe.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Created by zhouhh2 on 2017/1/5.
 */

@Configuration
@PropertySources({
        @PropertySource({Constants.CFG_FILE_DB}),
        @PropertySource({Constants.CFG_FILE_MYBATIS}),
//        @PropertySource(value = Constants.CFG_FILE_CACHE, ignoreResourceNotFound = true),
        @PropertySource(value = Constants.CFG_FILE_CAPTCHA),
        @PropertySource(value = Constants.CFG_FILE_ACCESS),
        @PropertySource(value = Constants.CFG_FILE_SECURITY)
})
@ComponentScan({"com"})
@EnableAsync
public class EnvConfig {
}
