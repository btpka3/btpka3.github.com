package me.test.first.spring.boot.jersey

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class MyJerseyApp {

    static void main(String[] args) throws Exception {
        SpringApplication.run(MyJerseyApp.class, args);
    }

}
