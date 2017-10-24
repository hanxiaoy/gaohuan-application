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
    public void testSelect2() {
        String sql = "select * from test_1 a where a.phone = '15811056271' and  a.id = ? ";
        List<Test1> list = jdbcService.doQuery(sql, Collections.singletonList("1"), Test1.class);
        assertThat(list.get(0).getPhone()).isEqualTo("15811056271");
    }

    @Test
    public void testSelect3() {
        String sql = "select a.id,a.phone,a.user_name from test_1 a where a.phone = '15811056271' and  a.id = ? ";
        List<Test1> list = jdbcService.doQuery(sql, Collections.singletonList("1"), Test1.class);
        assertThat(list.get(0).getPhone()).isEqualTo("15811056271");
    }

    @Test
    public void testSelect4() {
        String sql = "select a.id,a.phone,a.user_name from test_1 a ,test_2 b where a.user_name=b.user_name and  b.phone = '15811056271' and  a.id = ? ";
        List<Test1> list = jdbcService.doQuery(sql, Collections.singletonList("1"), Test1.class);
        assertThat(list.get(0).getPhone()).isEqualTo("15811056271");
    }

    @Test
    public void testUpdate1() {
        String sql = "update test_2 set phone=?, user_name = '123' where id =2";
        jdbcService.doUpdate(sql, Collections.singletonList("15811056271"));
    }
}
