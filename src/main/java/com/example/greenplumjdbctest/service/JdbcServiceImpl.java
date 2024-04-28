package com.example.greenplumjdbctest.service;


import com.example.greenplumjdbctest.entity.GreenplumJdbcProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 不用连接池
@Configuration
@ConditionalOnProperty(name = "pool.type", havingValue = "jdbc")
@Slf4j
public class JdbcServiceImpl {
    @Autowired
    GreenplumJdbcProperties greenplumJdbcProperties;

    @Value("${greenplum.sql}")
    private String sql;

    public List<Map<String,Object>> testSelect1() {
        log.info("========================>no datasource pool,use jdbc");
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Map<String, Object>> results = new ArrayList<>();
        try {
            // 加载JDBC驱动
            Class.forName("org.postgresql.Driver");
            String urlAll = String.format("%s/%s", greenplumJdbcProperties.getUrl(), greenplumJdbcProperties.getDb());
            // 建立数据库连接
            connection = DriverManager.getConnection(urlAll, greenplumJdbcProperties.getUser(), greenplumJdbcProperties.getPasswd());
            // 创建Statement对象
            statement = connection.createStatement();
            // 执行查询
            resultSet = statement.executeQuery(sql);
            // 处理查询结果
            while (resultSet.next()) {
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
        } catch (ClassNotFoundException | SQLException e) {
            log.info(String.format("========================>execute the sql error %s", e.getMessage()));
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                log.info(String.format("========================>execute the sql error %s", e.getMessage()));
                e.printStackTrace();
            }
        }
        return results;
    }

}
