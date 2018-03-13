package com.zhp.db.config;

import com.zhp.db.base.Constants;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlyWayConfig {
    private Flyway flyway;
    public static final String LOCATION = "/db/migration";

    @Autowired
    private DataSourceConfigRegister dataSourceConfigRegister;

    @Bean
    @ConditionalOnProperty(name = Constants.flag_initMigrate)
    public Flyway flyway() {
        if (dataSourceConfigRegister.getAppDBConfig().isOtherDs()) {
            initFlyway(dataSourceConfigRegister.getOthersDataSources().get(Constants.flywayDs));
        } else {
            initFlyway(dataSourceConfigRegister.getMasterDataSource());
        }
        flyway.repair();
        flyway.migrate();
        return flyway;
    }

    private void initFlyway(DataSource dataSource) {
        flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.setBaselineOnMigrate(true);
        flyway.setLocations(this.LOCATION);
        flyway.setEncoding("UTF-8");
        flyway.setOutOfOrder(true);
    }
}
