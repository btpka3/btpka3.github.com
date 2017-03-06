package me.test.first.spring.boot.data.mongo.controller

import me.test.first.spring.boot.data.mongo.domain.User
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
class MyTestController {

    // 测试 action 参数 id -> domain
    @RequestMapping(path = "/user/{id}", method = [RequestMethod.GET])
    User show(@PathVariable("id") User user) {
        return user
    }


}
