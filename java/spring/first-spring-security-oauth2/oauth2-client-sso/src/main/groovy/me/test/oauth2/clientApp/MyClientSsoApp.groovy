package me.test.oauth2.clientApp

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class MyClientSsoApp {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MyClientSsoApp.class, args);
    }

}
