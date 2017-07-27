package com.gaohuan.spring;

import com.gaohuan.spring.service.DemoService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author gao.h  2017-04-14
 */
public class SpringMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:application-context.xml");
        DemoService demoService = applicationContext.getBean(DemoService.class);
        demoService.print1("t1", "t2");
    }
}
