package me.test.oauth2.resource

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@EnableAutoConfiguration
@SpringBootApplication
class MyOAuthServerApp {

    @RequestMapping("/")
    String index() {
        return "Hello World!~ " + new Date();
    }

    @RequestMapping("/access")
    String access() {
        return "access denied! " + new Date();
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MyOAuthServerApp.class, args);
    }
}
