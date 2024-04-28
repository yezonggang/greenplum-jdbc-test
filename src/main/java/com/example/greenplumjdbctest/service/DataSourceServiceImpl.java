package com.example.greenplumjdbctest.service;


import com.alibaba.druid.pool.DruidDataSource;
import com.example.greenplumjdbctest.framwork.UseConnectionPoolCondition;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author y25958
 */
@Service
@Slf4j
@Conditional(UseConnectionPoolCondition.class)
public class DataSourceServiceImpl {

    @Autowired
    DataSource dataSource;

    @Value("${greenplum.sql}")
    private String sql;

    @Value("${pool.type}")
    private String poolType;

    public List<Map<String,Object>> testSelect1(){
        if("hikari".equalsIgnoreCase(poolType)){
            HikariDataSource hikariDataSource = (HikariDataSource) dataSource;
            log.info(String.format("========================>init the greenplum datasource bean success, datasource type %s", hikariDataSource.getPoolName()));
        }else {
            DruidDataSource druidDataSource = (DruidDataSource) dataSource;
            log.info(String.format("========================>init the greenplum datasource bean success, datasource type %s", druidDataSource.getName()));
        }
        List<Map<String, Object>> results = new ArrayList<>();
        try(Connection connection = dataSource.getConnection()) {
            log.info("========================>get the greenplum datasource success.");
            try(PreparedStatement statement = connection.prepareStatement(sql)) {
                log.info("========================>get the greenplum statement success, datasource type .");
                try(ResultSet resultSet = statement.executeQuery()){
                    log.info("========================>get the greenplum resultSet success, datasource type.");
                    while (resultSet.next()){
                        Map<String, Object> row = new HashMap<>();
                        ResultSetMetaData metaData = resultSet.getMetaData();
                        int columnCount = metaData.getColumnCount();
                        for(int i=1;i<=columnCount;i++){
                            String columnName = metaData.getColumnName(i);
                            Object object = resultSet.getObject(columnName);
                            row.put(columnName, object);
                        }
                        results.add(row);
                        log.info(String.format("========================>get the result,result size is %s", results.size()));
                    }
                }
            }
        }catch (SQLException e){
            log.info(String.format("========================>execute the sql error %s", e.getMessage()));
            e.printStackTrace();
        }
        return results;
    }
}
