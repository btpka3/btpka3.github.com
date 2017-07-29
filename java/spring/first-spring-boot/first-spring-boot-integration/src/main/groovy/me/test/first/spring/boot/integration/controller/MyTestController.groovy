package me.test.first.spring.boot.integration.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.integration.core.MessagingTemplate
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@RequestMapping("/test")
@Controller
class MyTestController {

    @Autowired
    MessagingTemplate msgTemplate

    @RequestMapping("")
    @ResponseBody
    Object test() {
        return "test " + new Date();
    }

    @RequestMapping("/send")
    @ResponseBody
    Object send(
            @RequestParam
                    String msg
    ) {

        msgTemplate.convertAndSend([
                msg: msg,
                date: new Date()
        ])
        return "sendOk : " + new Date()
    }


}
