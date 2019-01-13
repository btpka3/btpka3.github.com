package me.test.demo.acl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 当千
 * @date 2019-01-07
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        JdbcTemplateTest.Application.class
})
public class JdbcTemplateTest {

    private MutableAclService mutableAclService;


    @Autowired
    JdbcTemplate jdbcTemplate;

    @SpringBootApplication(exclude = FlywayAutoConfiguration.class)
    public static class Application {

    }

    @Test
    public void test01() {
        Integer i = jdbcTemplate.queryForObject("select 1+1", Integer.class);
        System.out.println(i);
    }
}
