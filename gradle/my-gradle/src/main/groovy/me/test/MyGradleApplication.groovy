package me.test

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class MyGradleApplication {

    static void main(String[] args) {
        println "111222"
        SpringApplication.run MyGradleApplication, args
    }
}
