package me.test.oauth2.resource.conf

import me.test.oauth2.resource.MyOAuth2Properties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.RemoteTokenServices

/**
 *
 * FIXME : @EnableResourceServer -> ResourceServerConfiguration 已经提供了一些配置，能否重用？
 */
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConf extends ResourceServerConfigurerAdapter {


    @Autowired
    private RemoteTokenServices remoteTokenServices

    @Bean
    MyOAuth2Properties myOAuth2Properties() {
        return new MyOAuth2Properties()
    }

    // ResourceServerTokenServicesConfiguration 有自动配置


    @Bean
    RemoteTokenServices remoteTokenServices(RestTemplateBuilder restTemplateBuilder) {
        RemoteTokenServices ts = new RemoteTokenServices()
        // 去检查token的URL
        ts.setCheckTokenEndpointUrl("http://a.localhost:10001/oauth/check_token");
        // 去检查token时，自己的身份信息
        ts.setClientId("CLIENT_ID_rsc_server");
        ts.setClientSecret("CLIENT_PWD_rsc_server");
        // 内部默认自己新建的，为了方便跟踪调试，统一使用自己配置的。
        ts.setRestTemplate(restTemplateBuilder.build())
        return ts
    }

    // 先调用 @ ResourceServerConfiguration.configure(HttpSecurity)
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {

        // 通过 application.yml "security.oauth2.resource.id" 配置
        resources.resourceId("RSC_ID_rsc_server")
                .tokenServices(remoteTokenServices) //RemoteTokenServices
                .stateless(true);
    }

    // 后调用 @ ResourceServerConfiguration.configure(HttpSecurity)
    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.requestMatchers()
                .antMatchers("/o2/**")

            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            // 权限的配置建议统一使用 @PreAuthorize 等注解来处理。
            .and()
                // FIXME : 必须调用该语句，否则 IllegalStateException @ ResourceServerSecurityConfigurer.configure() -> http.authorizeRequests()
                .authorizeRequests()
                .anyRequest()
                .authenticated()

            .and()
                .cors()
                .disable()

        // @formatter:on
    }

}















