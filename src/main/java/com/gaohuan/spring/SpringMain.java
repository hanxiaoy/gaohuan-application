package com.gaohuan.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author gao.h  2017-04-14
 */
public class SpringMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:application-context.xml");
        String jobExpression = applicationContext.getBean(ValueComponent.class).getJobExpression();
        System.out.println(jobExpression);
    }
}
