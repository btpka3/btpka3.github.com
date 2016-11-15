package my.grails3

import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.core.userdetails.UserDetailsService

//@EnableAutoConfiguration
@ComponentScan("my.grails3")
//@EnableDiscoveryClient
//@EnableEurekaClient
//@SpringBootApplication
@Configuration
class Application extends GrailsAutoConfiguration {
    static void main(String[] args) {
        println("============================init")
        GrailsApp.run(Application)
    }

    @Bean(name = ["ffuserDetailsService"] ) // FIXME 如果容器中已经有了同名的bean,则不会再调用该方法。
    @Primary
    UserDetailsService ssuserDetailsService() {
        // FIXME not work
        println("============================111111 userDetailsService")
        new MyUserDetails()
    }

    @Override
    Closure doWithSpring() {
        {->
            println "===================== doWithSpring : it"
            userDetailsService(MyUserDetails)  // 只有这里, resources.groovy 可以override同名的bean定义。
        }
    }
}