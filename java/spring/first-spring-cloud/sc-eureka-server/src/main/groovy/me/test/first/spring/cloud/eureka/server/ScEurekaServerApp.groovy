package me.test.first.spring.cloud.eureka.server

import groovy.transform.CompileStatic
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 *
 */
@SpringBootApplication
@CompileStatic
class ScEurekaServerApp {

    static void main(String[] args) throws Exception {
        SpringApplication.run(ScEurekaServerApp.class, args)
    }

}
