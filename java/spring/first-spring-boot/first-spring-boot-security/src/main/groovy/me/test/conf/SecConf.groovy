package me.test.conf

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.context.annotation.Bean
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

// @EnableWebSecurity(debug = false) 有该注解的话，就不会对 SpringBootWebSecurityConfiguration 进行配置了
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
class SecConf {

    @Autowired
    void configAuthenticationManager(AuthenticationManagerBuilder auth) {
        // @formatter:off
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("admin")
                .authorities("AAA")
                .roles("ADMIN", "USER")

            .and()
                .withUser("user")
                .password("user")
                .authorities("UUU")
                .roles("USER")

            .and()
                .withUser("basic")
                .password("basic")
                .authorities("UUU")
                .roles("U");
        // @formatter:on
    }


    @Bean
    @Order(SecurityProperties.DEFAULT_FILTER_ORDER)
    WebSecurityConfigurerAdapter webSecurityConfigurerAdapter() {
        return new WebSecurityConfigurerAdapter() {

            // 仅仅配置 URL 相关的安全设置，方法级别的通过注解处理
            @Override
            protected void configure(HttpSecurity http) throws Exception {
                // @formatter:off
                http.requestMatchers()                    // 如果不配置，则默认匹配所有请求
                        .antMatchers("/SecConf/pub.html") // 只匹配这几个请求的URL路径
                        .antMatchers("/SecConf/sec.html")
                        .antMatchers("/SecConf/adm.html")
                        .antMatchers("/login")
                        .antMatchers("/")
                        .antMatchers("/controller/pub")
                        .antMatchers("/controller/sec")
                        .antMatchers("/controller/adm")

                    .and()
                        .authorizeRequests()
                        .antMatchers("/SecConf/pub.html")
                        .permitAll()

                        .antMatchers("/SecConf/sec.html")
                        .authenticated()

                        .antMatchers("/SecConf/adm.html")
                        .access("isAuthenticated() && hasAnyRole('ADMIN')")

                        .antMatchers("/login")
                        .permitAll()

//                        .anyRequest()   // 禁用该配置，以方便测试默认的 http basic 认证
//                        .authenticated()

                    .and()
                        .formLogin()
                        .loginPage("/login")  // 这个如果不明确调用，会通过 DefaultLoginPageConfigurer 注册 DefaultLoginPageGeneratingFilter
//                        .failureUrl("/login?error")

                    //.and()                    // 默认值
                        //.logout()
                        //.logoutUrl("/logout")
                        //.logoutSuccessUrl("/login?logout")

//                    .and()
//                        .exceptionHandling()  // 默认已经配置
//                        .accessDeniedPage("/access?error")  // 如果没设置，则会通过错误码去找相应的错误画面。
                    .and()
                        .httpBasic()
                        .realmName("MySecApp")

                    .and()
                        .csrf()
                        .ignoringAntMatchers("/login")

                // @formatter:on
            }

//            // 这里的配置只会在 AuthenticationManagerConfiguration 之后运行
//            @Override
//            public void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//                // super.configure(auth); //
//
//                auth.inMemoryAuthentication()
//                        .withUser("admin")
//                        .password("admin")
//                        .authorities("AAA")
//                        .roles("ADMIN", "USER")
//
//                        .and()
//                        .withUser("user")
//                        .password("user")
//                        .authorities("UUU")
//                        .roles("USER");
//            }
        }
    }


}