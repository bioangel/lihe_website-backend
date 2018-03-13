package com.zhp.db.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Created by zhouhh2 on 2017/1/19.
 */

@Configuration
public class MybatisScanConfig implements EnvironmentAware {
    private Environment env;

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScan = new MapperScannerConfigurer();
        mapperScan.setBasePackage(env.getProperty("mybatis.mapperScan"));
        return mapperScan;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }
}
