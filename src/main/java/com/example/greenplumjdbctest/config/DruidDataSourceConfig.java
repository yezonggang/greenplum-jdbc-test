package com.example.greenplumjdbctest.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.example.greenplumjdbctest.entity.GreenplumJdbcProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author y25958
 */ // 使用druid连接池
@Configuration
@ConditionalOnProperty(name = "pool.type", havingValue = "druid")
public class DruidDataSourceConfig {

    @Autowired
    GreenplumJdbcProperties greenplumJdbcProperties;

    @Bean(name = "druid_datasource")
    public DataSource get(){
        String urlAll = String.format("%s/%s", greenplumJdbcProperties.getUrl(), greenplumJdbcProperties.getDb());
        String driverClass = "org.postgresql.Driver";
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(urlAll);
        druidDataSource.setDriverClassName(driverClass);
        druidDataSource.setUsername(greenplumJdbcProperties.getUser());
        druidDataSource.setPassword(greenplumJdbcProperties.getPasswd());
        druidDataSource.setMaxActive(2);
        druidDataSource.setInitialSize(2);
        druidDataSource.setMaxWait(60000);
        druidDataSource.setPoolPreparedStatements(true);
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);
        druidDataSource.setName("druid_pool");
        return druidDataSource;
    }
}
