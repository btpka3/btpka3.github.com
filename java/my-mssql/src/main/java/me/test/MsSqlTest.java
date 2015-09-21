package me.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class MsSqlTest {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("application-context.xml");

        JdbcTemplate jdbcTemplate = (JdbcTemplate) appContext.getBean("jdbcTemplate");

        Integer result = jdbcTemplate.queryForObject("select 1+2", Integer.class);

        System.out.println("result = " + result);

    }

}
