package com.gaohuan;

import com.gaohuan.service.JdbcService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by gaohuan on 2017/6/24.
 */

public class Main {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:application-context.xml");
        JdbcService jdbcService = context.getBean(JdbcService.class);
        List<Map<String, Object>> resultList = jdbcService.doExecute("select * from test_1 a where  a.phone=? ", Arrays.asList("15811056271"));
        if (CollectionUtils.isNotEmpty(resultList)) {
            for (Map<String, Object> result : resultList) {
                Integer id = (Integer) result.get("id");
                String phone = new String((byte[]) result.get("phone"), "utf-8");
                String bankNo = new String((byte[]) result.get("bank_no"), "utf-8");
                System.out.println(org.apache.commons.lang3.StringUtils.join(id, ",", phone, ",", bankNo));
            }
        }
    }


}
