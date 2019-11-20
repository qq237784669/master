package com.cgo.login.config.datasource;


import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DruidConfig {
    @Bean(name = "dataSource")
    @Primary
    @ConfigurationProperties(prefix = "druid")
    public DataSource dataSource() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();

    }
}
