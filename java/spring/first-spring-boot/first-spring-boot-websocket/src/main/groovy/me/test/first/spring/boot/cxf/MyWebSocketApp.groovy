package me.test.first.spring.boot.cxf

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class MyWebSocketApp {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MyWebSocketApp.class, args);
    }

}
