package me.test.controller

import me.test.service.MyService
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

    /** 测试 service bean 调用 */
    @RequestMapping("/jdbcTemplate")
    @ResponseBody
    Object jdbcTemplate() {

        return myService.testJdbcTemplate()
    }

    /** 测试 service bean 调用 */
    @RequestMapping("/jpa")
    @ResponseBody
    Object jpa() {

        return myService.testJpa()
    }

}
