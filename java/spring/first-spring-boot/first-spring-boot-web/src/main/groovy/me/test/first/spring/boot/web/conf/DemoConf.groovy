package me.test.first.spring.boot.web.conf

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration;

@Configuration
class DemoConf {

    @Bean
    List aaa() {
        return Arrays.asList("a1", "a2")
    }
}
