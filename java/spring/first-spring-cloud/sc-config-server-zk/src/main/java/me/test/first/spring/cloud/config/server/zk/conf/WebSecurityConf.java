package me.test.first.spring.cloud.config.server.zk.conf;

import org.springframework.boot.autoconfigure.security.*;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.provisioning.*;

/**
 * 对 Spring Security 进行配置
 *
 * 原则上，该安全配置应当不会处理任何请求。
 */
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConf {
    @Bean
    public AuthenticationTrustResolver authenticationTrustResolver() {
        return new AuthenticationTrustResolverImpl();
    }

    @Bean
    public UserDetailsService staffUserDetailsService() {
        InMemoryUserDetailsManager m = new InMemoryUserDetailsManager();

        UserDetails userDetails = User
                .withUsername("a_admin")
                .password("a_admin")
                .authorities("ADMIN", "USER")
                .build();
        m.createUser(userDetails);

        userDetails = User
                .withUsername("a_user")
                .password("a_user")
                .authorities("USER")
                .build();
        m.createUser(userDetails);

        return m;
    }

    @Bean
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    public WebSecurityConfigurerAdapter webSecurityConfigurerAdapter() {

        return new WebSecurityConfigurerAdapter() {
            @Override
            public void configure(AuthenticationManagerBuilder auth) {
                //auth.inMemoryAuthentication()
                //        .withUser("a_admin")
                //        .password("a_admin")
                //        .authorities("AAA")
                //        .roles("ADMIN", "USER")
            }

            @Override
            public void configure(WebSecurity web) throws Exception {
                // empty
            }

            @Override
            protected void configure(HttpSecurity http) throws Exception {

                // 只对以下路径规则应用该安全设置。
                http.requestMatchers()
                        .antMatchers("/**");

                http.authorizeRequests()
                        .anyRequest()
                        .permitAll();

                http.csrf()
                        .disable();
            }

        };
    }

}
