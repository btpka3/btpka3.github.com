package me.test.first.spring.boot.integration.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class MyHomeController {

    /** 测试最基本情形 */
    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "home " + new Date();
    }
}
