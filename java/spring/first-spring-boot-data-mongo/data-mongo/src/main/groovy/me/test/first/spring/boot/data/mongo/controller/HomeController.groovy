package me.test.first.spring.boot.data.mongo.controller

import me.test.first.spring.boot.data.mongo.core.Hi
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class HomeController {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "home " + new Date();
    }

    @RequestMapping("/test")
    @ResponseBody
    String test() {
        return "home " + Hi.hi("zhang3") + " : " + new Date();
    }

}
