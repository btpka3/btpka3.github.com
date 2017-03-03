package me.test.first.spring.boot.swagger.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class MyTestController {

    /** 测试最基本情形 */
    @RequestMapping(
            path = "/",
            method = RequestMethod.GET // 一定要明确写明方法，否则 swagger 会生成所有的
    )
    @ResponseBody
    String home() {
        return "home " + new Date();
    }


}
