package me.test

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HiController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/json")
    Object json() {
        // 没办法自动重新编译，重新加载
        [a: "aa", b: 1, c: true]
    }
}
