package my.grails3

import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.security.core.userdetails.UserDetailsService

@EnableAutoConfiguration
@ComponentScan("my.grails3")
@SpringBootApplication
class Application extends GrailsAutoConfiguration {
    static void main(String[] args) {
        println("============================init")
        GrailsApp.run(Application)
    }

    @Bean
    UserDetailsService userDetailsService() {
        // FIXME not work
        println("============================111111 userDetailsService")
        new MyUserDetails()
    }
}