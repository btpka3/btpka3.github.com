package me.test.first.spring.cloud.config

import groovy.transform.CompileStatic
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 *
 */
@SpringBootApplication(scanBasePackages="me.test.first.spring.cloud.config")
@CompileStatic
class ScConfigApp {

    static void main(String[] args) throws Exception {
        SpringApplication.run(ScConfigApp.class, args)
    }
}
