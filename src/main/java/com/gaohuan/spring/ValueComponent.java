package com.gaohuan.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author gao.h  2017-04-14
 */
@Component
public class ValueComponent {
    @Value("${job.expression}")
    private String jobExpression;

    public String getJobExpression() {
        return jobExpression;
    }

}
