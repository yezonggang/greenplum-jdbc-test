package com.example.greenplumjdbctest.controller;


import com.example.greenplumjdbctest.framwork.UseConnectionPoolCondition;
import com.example.greenplumjdbctest.service.DataSourceServiceImpl;
import com.example.greenplumjdbctest.service.JdbcServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author y25958
 */
@RestController
@RequestMapping("/greenplum")
@ConditionalOnProperty(name = "pool.type", havingValue = "jdbc")
public class GreenplumJdbcController {
    @Autowired
    JdbcServiceImpl jdbcService;

    @GetMapping("/select1")
    public int Select1(){
        List<Map<String, Object>> maps = jdbcService.testSelect1();
        if(maps.size()==0){
            return 0;
        }else {
            return (int) maps.get(0).get("result");
        }
    }
}
