package me.test.oauth2.clientSso.conf

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

/**
 * 对 Spring Security 进行配置
 */
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
class WebSecurityConf extends WebSecurityConfigurerAdapter {

    @Autowired
    void configAuthenticationManager(AuthenticationManagerBuilder auth) {
        // @formatter:off
        auth.inMemoryAuthentication()
                .withUser("c_admin")
                .password("c_admin")
                .authorities("AAA")
                .roles("ADMIN", "USER")

            .and()
                .withUser("c_user")
                .password("c_user")
                .authorities("UUU")
                .roles("USER")
        // @formatter:on
    }

    @Override
    public void configure(WebSecurity web) throws Exception {

        web.ignoring()
            .antMatchers("/implicit.html")

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        // @formatter:off
        // 只对以下路径规则应用该安全设置。
        http.requestMatchers()
                .antMatchers("/")
                .antMatchers("/login")
                .antMatchers("/sec")
                .antMatchers("/photo/**")

            .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()

            .and()
                .authorizeRequests()
                .antMatchers("/")
                .permitAll()

                .anyRequest()
                .authenticated()

            .and()
                .httpBasic()
                .realmName("My OAuth2 Client App")

            .and()
                .csrf()
                .ignoringAntMatchers("/**")
        // @formatter:on
    }

}