package com.example.greenplumjdbctest.framwork;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;  
  
/**
 * @author y25958
 */
public class UseConnectionPoolCondition implements Condition {
  
    @Override  
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {  
        String propertyName = "pool.type";
        String[] acceptableValues = {"hikari", "druid"};
        String propertyValue = context.getEnvironment().getProperty(propertyName);  
          
        for (String acceptableValue : acceptableValues) {  
            if (acceptableValue.equals(propertyValue)) {  
                return true;  
            }  
        }  
          
        return false;  
    }  
}