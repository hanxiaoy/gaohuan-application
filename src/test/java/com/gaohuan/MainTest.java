package com.gaohuan;

import com.gaohuan.service.JdbcService;
import com.gaohuan.vo.Test1;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by gaohuan on 2017/10/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:application-context.xml")
public class MainTest {
    private static final Logger logger = LoggerFactory.getLogger(MainTest.class);
    @Autowired
    private JdbcService jdbcService;

    @Test
    public void testSelect1() {
        String sql = "select * from test_1 where phone =  ?";
        List<Test1> list = jdbcService.doQuery(sql, Collections.singletonList("15811056271"), Test1.class);
        assertThat(list.get(0).getPhone()).isEqualTo("15811056271");
    }

    @Test
    public void testUpdate1() {
        String sql = "update test_1 set user_name='15811056272' where phone = ? and id =1";
        jdbcService.doUpdate(sql, Collections.singletonList("15811056271"));
    }
}
