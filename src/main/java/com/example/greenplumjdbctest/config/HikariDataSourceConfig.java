package com.example.greenplumjdbctest.config;


import com.example.greenplumjdbctest.entity.GreenplumJdbcProperties;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

// 使用hikari连接池
@Slf4j
@Configuration
@ConditionalOnProperty(name = "pool.type", havingValue = "hikari")
public class HikariDataSourceConfig {

    @Autowired
    GreenplumJdbcProperties greenplumJdbcProperties;

    @Value("${pool.test}")
    private boolean test;

    @Bean
    public DataSource get(){
        String urlAll = String.format("%s/%s", greenplumJdbcProperties.getUrl(), greenplumJdbcProperties.getDb());
        String driverClass = "org.postgresql.Driver";
        HikariDataSource dataSource = (HikariDataSource) DataSourceBuilder.create()
                .url(urlAll)
                .driverClassName(driverClass)
                .username(greenplumJdbcProperties.getUser())
                .password(greenplumJdbcProperties.getPasswd())
                .build();
        dataSource.setMaximumPoolSize(2);
        dataSource.setMinimumIdle(2);
        dataSource.setConnectionTimeout(15000);
        dataSource.setPoolName("hikari_pool");
        dataSource.setIdleTimeout(60000);
        dataSource.setMaxLifetime(180000);
        dataSource.setAutoCommit(true);
        if (test) {
            log.info("hikari setConnectionTestQuery true.");
            dataSource.setConnectionTestQuery("select 1 as heartbeat_now");
        }
        dataSource.setLeakDetectionThreshold(30000);
        return dataSource;
    }

}
