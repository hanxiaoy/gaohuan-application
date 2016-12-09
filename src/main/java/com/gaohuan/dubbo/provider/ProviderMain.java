package com.gaohuan.dubbo.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by gh on 2016/2/29 0029.
 */
public class ProviderMain {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"dubbo/provider.xml"});
        context.start();
        //任意键退出
        System.in.read();

    }
}
