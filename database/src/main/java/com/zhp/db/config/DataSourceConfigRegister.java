package com.zhp.db.config;

import com.zhp.db.base.BaseDBConfig;
import com.zhp.db.base.Constants;
import com.zhp.security.utils.SecurityUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by zhouhh2 on 2016/11/25.
 */
@Configuration
@Import({SecurityUtils.class})
public class DataSourceConfigRegister implements EnvironmentAware {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AppDBConfig appDBConfig;

    @Autowired
    private MasterDBConfig masterDBConfig;

    private DataSource masterDataSource;

    private Map<String, DataSource> customDataSources = new HashMap<>();

    private Map<String, DataSource> othersDataSources = new HashMap<>();

    private Map<Object, Object> targetDataSources = new HashMap<>();

    private PropertyValues dataSourcePropertyValues;

    private String customNames;

    @Autowired
    private SecurityUtils securityUtils;

    @Override
    public void setEnvironment(Environment environment) {
        this.initDefaultDataSource(environment);
        this.initCustomDataSources(environment);
        this.targetDataSources.put(Constants.defaultDs, this.masterDataSource);
        DynamicDataSourceContextHolder.dataSourceIds.add(Constants.defaultDs);

        this.targetDataSources.putAll(customDataSources);
        this.customDataSources.keySet().forEach(key -> DynamicDataSourceContextHolder.dataSourceIds.add(key));
        this.initOthersDataSource(environment);
        this.targetDataSources.putAll(othersDataSources);
        this.othersDataSources.keySet().forEach(key -> DynamicDataSourceContextHolder.dataSourceIds.add(key));
    }

    private void initDefaultDataSource(Environment environment) {
        this.masterDataSource = buildDataSource(masterDBConfig);
        this.setDatabaseCommonConfig(this.masterDataSource, environment);
        logger.info("Create master datasource");
    }

    private void setDatabaseCommonConfig(DataSource dataSource, Environment environment) {
        RelaxedDataBinder dataBinder = new RelaxedDataBinder(dataSource);
        dataBinder.setConversionService(new DefaultConversionService());
        dataBinder.setIgnoreNestedProperties(false);
        dataBinder.setIgnoreInvalidFields(false);
        dataBinder.setIgnoreUnknownFields(true);
        if (dataSourcePropertyValues == null) {
            Map<String, Object> rpr = new RelaxedPropertyResolver(environment,
                    Constants.PREFIX_COMMON).getSubProperties(".");
            Map<String, Object> values = new HashMap<>(rpr);
            dataSourcePropertyValues = new MutablePropertyValues(values);
        }
        dataBinder.bind(dataSourcePropertyValues);
    }

    public DataSource buildDataSource(BaseDBConfig baseDBConfig) {
        String type = Optional.ofNullable(this.appDBConfig.getType())
                .orElse(Constants.DATASOURCE_TYPE_DEFAULT);
        Class<? extends DataSource> dataSourceType;
        try {
            dataSourceType = (Class<? extends DataSource>) Class.forName(type);
            String driverClassName = baseDBConfig.getDriverClassName();
            String url = baseDBConfig.getUrl();
            String username = baseDBConfig.getUsername();
            String password = securityUtils.isEncryptPasswd()
                    ? securityUtils.decrypt(baseDBConfig.getPassword()) : baseDBConfig.getPassword();
            DataSourceBuilder factory = DataSourceBuilder.create().driverClassName(driverClassName).url(url)
                    .username(username).password(password).type(dataSourceType);
            return factory.build();
        } catch (ClassNotFoundException e) {
            logger.error("Database class not found!\nException message: {}",
                    ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    private void initCustomDataSources(Environment environment) {
        if (!this.appDBConfig.isMultipleDs()) {
            return;
        }
        RelaxedPropertyResolver customDs =
                new RelaxedPropertyResolver(environment, Constants.PREFIX_CUSTOM + ".");
        this.customNames = customDs.getProperty("names");
        String port = customDs.getProperty("port");
        for (String dsPrefix : this.customNames.split(",")) {
            DataSource ds = createDs(environment, customDs, dsPrefix, Constants.PREFIX_CUSTOM, port);
            this.customDataSources.put(dsPrefix, ds);
            logger.info("Create custom datasource {}", dsPrefix);
        }
    }

    private void initOthersDataSource(Environment environment) {
        if (!this.appDBConfig.isOtherDs()) {
            return;
        }
        RelaxedPropertyResolver othersDs =
                new RelaxedPropertyResolver(environment, Constants.PREFIX_OTHERS + ".");
        String others = othersDs.getProperty("names");
        String port = othersDs.getProperty("port");
        for (String dsPrefix : others.split(",")) {
            DataSource ds = createDs(environment, othersDs, dsPrefix, Constants.PREFIX_OTHERS, port);
            this.othersDataSources.put(dsPrefix, ds);
            logger.info("Create other datasource {}", dsPrefix);
        }
    }

    private DataSource createDs(Environment environment, RelaxedPropertyResolver rpr,
                                String dsPrefix, String flag, String port) {
        Map<String, Object> dsMap = rpr.getSubProperties(dsPrefix + ".");
        DataSource ds = this.buildDataSource(
                new CustomDBConfig(dsMap.get("driverClassName").toString(),
                        dsMap.get("url").toString().replace(flag + ".port", port),
                        dsMap.get("username").toString(),
                        dsMap.get("password").toString()));
        this.setDatabaseCommonConfig(ds, environment);
        try {
            ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ds;
    }

    @Component
    @ConfigurationProperties(prefix = Constants.PREFIX_DB)
    public static class AppDBConfig {
        private String type;
        private boolean multipleDs;
        private boolean initMigrate;
        private boolean otherDs;

        public boolean isOtherDs() {
            return otherDs;
        }

        public void setOtherDs(boolean otherDs) {
            this.otherDs = otherDs;
        }

        public boolean isMultipleDs() {
            return multipleDs;
        }

        public void setMultipleDs(boolean multipleDs) {
            this.multipleDs = multipleDs;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean isInitMigrate() {
            return initMigrate;
        }

        public void setInitMigrate(boolean initMigrate) {
            this.initMigrate = initMigrate;
        }
    }

    @Component
    @ConfigurationProperties(prefix = Constants.PREFIX_MASTER)
    static class MasterDBConfig extends BaseDBConfig {
    }

    class CustomDBConfig extends BaseDBConfig {
        private boolean other;

        public boolean isOther() {
            return other;
        }

        public void setOther(boolean other) {
            this.other = other;
        }

        public CustomDBConfig(String driverClassName, String url, String username, String password) {
            super(driverClassName, url, username, password);
        }
    }

    public Map<Object, Object> getTargetDataSources() {
        return targetDataSources;
    }

    public DataSource getMasterDataSource() {
        return masterDataSource;
    }

    public AppDBConfig getAppDBConfig() {
        return appDBConfig;
    }

    public String getCustomNames() {
        return customNames;
    }

    public Map<String, DataSource> getOthersDataSources() {
        return othersDataSources;
    }

    public String getCustomReadDataSource() {
        String[] ds = this.getCustomNames().split(",");
        if (ds == null || ds.length == 0) {
            return null;
        }
        return ds[(int) (Math.random() * ds.length)];
    }
}
