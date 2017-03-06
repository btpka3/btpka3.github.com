package me.test.first.spring.boot.cxf.acl.conf

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.context.annotation.Bean
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

// @EnableWebSecurity(debug = false) 有该注解的话，就不会对 SpringBootWebSecurityConfiguration 进行配置了
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
class SecConf {

    @Autowired
    void configAuthenticationManager(AuthenticationManagerBuilder auth) {
        // @formatter:off
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("admin")
                .authorities("ADMIN", "USER")

            .and()
                .withUser("user")
                .password("user")
                .authorities("USER")

            .and()
                .withUser("zhang3")
                .password("zhang3")
                .authorities("USER")

            .and()
                .withUser("li4")
                .password("li4")
                .authorities("USER")
        // @formatter:on
    }


    @Bean
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    WebSecurityConfigurerAdapter webSecurityConfigurerAdapter() {
        return new WebSecurityConfigurerAdapter() {

            // 仅仅配置 URL 相关的安全设置，方法级别的通过注解处理
            @Override
            protected void configure(HttpSecurity http) throws Exception {
                // @formatter:off
                http.requestMatchers()
                        .antMatchers("/login")
                        .antMatchers("/")
                        .antMatchers("/controller/**")
                        .antMatchers("/acl/**")

                    .and()
                        .authorizeRequests()
                        .antMatchers("/login")
                        .permitAll()

                    .and()
                        .formLogin()
                        .loginPage("/login")

                    .and()
                        .httpBasic()
                        .realmName("MySecApp")

                    .and()
                        .csrf()
                        .ignoringAntMatchers("/login")
                    .and()
                        .logout()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"));
                // @formatter:on
            }
        }
    }


}