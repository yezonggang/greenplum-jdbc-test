package com.example.greenplumjdbctest.entity;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author y25958
 */
@Component
@Data
@ConfigurationProperties(prefix = "greenplum")
public class GreenplumJdbcProperties {
    private String user;
    private String passwd;
    private String db;
    private String url;
    private String sql;
}
