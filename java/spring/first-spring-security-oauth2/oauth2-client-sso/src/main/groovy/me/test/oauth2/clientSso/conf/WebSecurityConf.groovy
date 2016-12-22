package me.test.oauth2.clientSso.conf

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

/**
 * 对 Spring Security 进行配置
 */
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
//@EnableOAuth2Sso // 启用 OAuth2SsoCustomConfiguration
// 引入的 SsoSecurityConfigurer 配置不够灵活，不再使用。
class WebSecurityConf extends WebSecurityConfigurerAdapter {

    @Autowired
    OAuth2SsoConf oauth2SsoConf

    @Override
    public void configure(WebSecurity web) throws Exception {

        web.ignoring()
                .antMatchers("/ajaxSso.html")

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.apply(oauth2SsoConf)

        // @formatter:off
        // 只对以下路径规则应用该安全设置。
        http.requestMatchers()
                .antMatchers("/")
                .antMatchers("/login")
                .antMatchers("/sec")

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