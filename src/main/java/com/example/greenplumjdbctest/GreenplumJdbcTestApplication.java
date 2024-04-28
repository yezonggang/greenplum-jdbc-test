package com.example.greenplumjdbctest;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.example.greenplumjdbctest.entity.GreenplumJdbcProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author y25958
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,DruidDataSourceAutoConfigure.class})
@EnableConfigurationProperties(GreenplumJdbcProperties.class)
public class GreenplumJdbcTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(GreenplumJdbcTestApplication.class, args);
    }

}
