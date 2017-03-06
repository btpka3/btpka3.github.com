package me.test.first.spring.boot.cxf.db.controller

import me.test.first.spring.boot.cxf.db.service.MyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class MyTestController {

    @Autowired
    MyService myService

    /** 测试最基本情形 */
    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "home " + new Date();
    }

    /** 测试 JdbcTemplate */
    @RequestMapping("/jdbcTemplate")
    @ResponseBody
    Object jdbcTemplate() {

        return myService.testJdbcTemplate()
    }

    /** 测试 JPA */
    @RequestMapping("/jpa")
    @ResponseBody
    Object jpa() {

        return myService.testJpa()
    }

    /** 测试 spring-data-jpa */
    @RequestMapping("/dataJpa")
    @ResponseBody
    Object dataJpa() {

        return myService.testDataJpa()
    }

}
