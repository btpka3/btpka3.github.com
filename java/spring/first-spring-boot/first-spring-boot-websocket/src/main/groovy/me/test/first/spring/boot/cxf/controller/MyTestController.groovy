package me.test.first.spring.boot.cxf.controller

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class MyTestController {

    /** 测试最基本情形 */
    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "home " + new Date();
    }

    /** 测试 service bean 调用 */
    @MessageMapping("/myHi")
    //@SendTo("/topic/greetings")
    String hi(String reqMsg) {
        return "RE : HI : ${reqMsg}"
    }

}
