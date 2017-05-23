package me.test.first.spring.cloud.eureka.sc

import groovy.transform.CompileStatic
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 *
 */
@SpringBootApplication
@CompileStatic
class ScEurekaScApp {

    static void main(String[] args) throws Exception {
        SpringApplication.run(ScEurekaScApp.class, args)
    }

}
