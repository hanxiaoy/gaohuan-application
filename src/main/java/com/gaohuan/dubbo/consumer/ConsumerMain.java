package com.gaohuan.dubbo.consumer;

import com.gaohuan.dubbo.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by gh on 2016/2/29 0029.
 */
public class ConsumerMain {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"dubbo/consumer.xml"});
        context.start();
        DemoService demoService = (DemoService) context.getBean("demoService");
        String hello = demoService.sayHello("gaohuan");
        System.out.println("---------------------" + hello + "---------------------");
    }
}
