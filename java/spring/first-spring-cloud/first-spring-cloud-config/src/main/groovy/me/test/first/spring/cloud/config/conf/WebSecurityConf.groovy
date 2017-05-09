package me.test.first.spring.cloud.config.conf

import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.context.annotation.Bean
import org.springframework.core.annotation.Order
import org.springframework.security.authentication.AuthenticationTrustResolver
import org.springframework.security.authentication.AuthenticationTrustResolverImpl
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager

/**
 * 对 Spring Security 进行配置
 *
 * 原则上，该安全配置应当不会处理任何请求。
 */
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
class WebSecurityConf {

    @Bean
    AuthenticationTrustResolver authenticationTrustResolver() {
        return new AuthenticationTrustResolverImpl()
    }


    @Bean
    UserDetailsService staffUserDetailsService() {
        InMemoryUserDetailsManager m = new InMemoryUserDetailsManager();

        UserDetails userDetails = User.withUsername("a_admin")
                .password("a_admin")
                .authorities("ADMIN", "USER")
                .build()
        m.createUser(userDetails);

        userDetails = User.withUsername("a_user")
                .password("a_user")
                .authorities("USER")
                .build()
        m.createUser(userDetails);

        return m
    }

    @Bean
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    WebSecurityConfigurerAdapter webSecurityConfigurerAdapter() {

        return new WebSecurityConfigurerAdapter() {

            @Override
            void configure(AuthenticationManagerBuilder auth) {
                //auth.inMemoryAuthentication()
                //        .withUser("a_admin")
                //        .password("a_admin")
                //        .authorities("AAA")
                //        .roles("ADMIN", "USER")
            }

            @Override
            void configure(WebSecurity web) throws Exception {
                // empty
            }

            @Override
            protected void configure(HttpSecurity http) throws Exception {

                // 只对以下路径规则应用该安全设置。
                http.requestMatchers()
                        .antMatchers("/**")

                http.authorizeRequests()
                        .anyRequest()
                        .permitAll()

                http.csrf() // 仅仅测试用
                        .disable()
            }

        }
    }

}