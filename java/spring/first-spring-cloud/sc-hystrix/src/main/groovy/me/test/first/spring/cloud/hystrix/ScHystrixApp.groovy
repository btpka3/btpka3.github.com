package me.test.first.spring.cloud.hystrix

import groovy.transform.CompileStatic
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 *
 */
@SpringBootApplication
@CompileStatic
class ScHystrixApp {

    static void main(String[] args) throws Exception {
        SpringApplication.run(ScHystrixApp.class, args)
    }

}
