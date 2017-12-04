package me.test.first.spring.boot.batch.controller

import org.springframework.web.bind.annotation.*

@RestController
class HomeController {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "home " + new Date();
    }

}
