package com.gaohuan;

import com.alibaba.fastjson.JSON;
import com.gaohuan.service.JdbcService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;
import java.util.Arrays;

/**
 * Created by gaohuan on 2017/6/24.
 */

public class Main {
    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:application-context.xml");
        JdbcService jdbcService = context.getBean(JdbcService.class);
        Object result = jdbcService.doExecute("select * from test_1 a,test_2 b where a.user_name = b.user_name and b.phone=? and a.bank_no=?", Arrays.asList("15811056271","6228480402564890018"));
        System.out.println(JSON.toJSONString(result));
    }


}
