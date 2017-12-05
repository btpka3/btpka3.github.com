package me.test.oauth2.authorization.conf

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler

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
                .withUser("a_admin")
                .password("a_admin")
                .authorities("AAA")
                .roles("ADMIN", "USER")

            .and()
                .withUser("a_user")
                .password("a_user")
                .authorities("UUU")
                .roles("USER")

            .and()
                .withUser("a_my_rsc")
                .password("a_my_rsc")
                .authorities("UUU")
                .roles("RSC")
        // @formatter:on
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//
//        web.expressionHandler(new OAuth2MethodSecurityExpressionHandler())
//
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {



        // @formatter:off
        // 只对以下路径规则应用该安全设置。
        http.requestMatchers()
                .antMatchers("/")
                .antMatchers("/login")
                .antMatchers("/sec")
                .antMatchers("/oauth/authorize")
                .antMatchers("/oauth/confirm_access")
                .antMatchers("/oauth/check_token")

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
                .realmName("My OAuth2 Auth Server")

            .and()
                .csrf() // 仅仅测试用
                .ignoringAntMatchers("/login")
                .ignoringAntMatchers("/oauth/confirm_access")
                .ignoringAntMatchers("/oauth/authorize")
        // @formatter:on
    }

}