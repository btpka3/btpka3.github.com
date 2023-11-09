package me.test.first.spring.boot.web.conf

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.env.Environment

import java.util.stream.Collectors
import java.util.stream.Stream

@Configuration
public class ProfileConf {

    @Autowired
    Environment env;

    @Bean
    Object bb() {
        return "bb";
    }

    @Bean(name = "aa")
    @Profile("a1")
    Object aa() {
        return "aa-a1";
    }

    @Bean(name = "aa")
    @Profile("a2")
    Object aa1(@Qualifier("bb") Object bb) {
        return "aa-a2," + bb
    }

    @Autowired
    void cc(@Qualifier("aa") Object aa) {
        println "====ENV = " + Stream.of(env.getActiveProfiles()).collect(Collectors.joining(","))

        // FIXME 两个 aa 方法都在一个类中 不起作用？@Profile 的javadoc 中 "NOTE: "
        // 此时，最好使用不同的方法名，但明确指明相同的 bean name
        System.out.println("----------- aa=" + aa);
    }
}
