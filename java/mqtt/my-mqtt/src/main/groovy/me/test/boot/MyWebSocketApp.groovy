package me.test.boot

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(scanBasePackages = ["me.test"])
class MyWebSocketApp {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MyWebSocketApp.class, args);
    }

}
