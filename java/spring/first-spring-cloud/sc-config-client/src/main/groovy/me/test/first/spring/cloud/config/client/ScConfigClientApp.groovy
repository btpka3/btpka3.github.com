package me.test.first.spring.cloud.config.client

import groovy.transform.CompileStatic
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 *
 */
@SpringBootApplication
//@EnableDiscoveryClient
@CompileStatic
class ScConfigClientApp {

    static void main(String[] args) throws Exception {
        SpringApplication.run(ScConfigClientApp.class, args)
    }
}
