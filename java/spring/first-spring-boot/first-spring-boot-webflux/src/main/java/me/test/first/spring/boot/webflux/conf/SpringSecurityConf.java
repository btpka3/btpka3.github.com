package me.test.first.spring.boot.webflux.conf;

import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @author dangqian.zll
 * @date 2022/2/18
 */
@Configuration(proxyBeanMethods = false)
public class SpringSecurityConf {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange((spec) -> {
            spec.matchers(PathRequest.toStaticResources().atCommonLocations()).permitAll();
            spec.pathMatchers("/foo", "/bar").authenticated();
        });
        http.formLogin();
        return http.build();
    }

}
