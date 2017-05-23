package me.test.first.spring.cloud.ribbon

import groovy.transform.CompileStatic
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 *
 */
@SpringBootApplication
@CompileStatic
class ScRibbonApp {

    static void main(String[] args) throws Exception {
        SpringApplication.run(ScRibbonApp.class, args)
    }

}
