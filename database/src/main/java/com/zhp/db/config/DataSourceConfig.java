package com.zhp.db.config;

import com.zhp.db.base.Constants;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import java.util.Optional;

@Configuration
@EnableTransactionManagement
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})
@AutoConfigureAfter({DataSourceConfigRegister.class, MybatisProperties.class})
public class DataSourceConfig implements TransactionManagementConfigurer {

    @Autowired
    private DataSourceConfigRegister dataSourceConfigRegister;

    @Autowired
    private MybatisProperties mybatisProperties;

    @Primary
    @Bean(name = Constants.defaultDs)
    public DynamicDataSource getDefaultDs() {
        DynamicDataSource dds = new DynamicDataSource();
        dds.setTargetDataSources(dataSourceConfigRegister.getTargetDataSources());
        dds.setDefaultTargetDataSource(dataSourceConfigRegister.getMasterDataSource());
        return dds;
    }

    @Bean
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(this.getDefaultDs());
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(mybatisProperties.getMapperLocations()));
        sqlSessionFactoryBean
                .setConfigLocation(new DefaultResourceLoader()
                        .getResource(mybatisProperties.getConfigLocation()));
        Optional.ofNullable(mybatisProperties.getTypeAliasesPackage())
                .ifPresent(e -> sqlSessionFactoryBean.setTypeAliasesPackage(e));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = Constants.defaultTx)
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(this.getDefaultDs());
    }
}
