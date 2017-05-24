package me.test.first.spring.boot.feign

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class MyFeignApp {

    static void main(String[] args) throws Exception {
        SpringApplication.run(MyFeignApp.class, args);
    }

}
