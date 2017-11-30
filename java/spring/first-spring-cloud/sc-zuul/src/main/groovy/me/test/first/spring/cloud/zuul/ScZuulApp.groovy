package me.test.first.spring.cloud.zuul

import groovy.transform.CompileStatic
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.zuul.EnableZuulProxy

/**
 *
 */
@SpringBootApplication
@EnableZuulProxy
@CompileStatic
class ScZuulApp {

    static void main(String[] args) throws Exception {
        SpringApplication.run(ScZuulApp.class, args)
    }

}
