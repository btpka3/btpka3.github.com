package me.test.first.spring.cloud.config.server

import groovy.transform.CompileStatic
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

/**
 *
 */
@SpringBootApplication
//@EnableDiscoveryClient
@CompileStatic
class ScConfigServerApp {

    static void main(String[] args) throws Exception {
        SpringApplication.run(ScConfigServerApp.class, args)
    }
}
