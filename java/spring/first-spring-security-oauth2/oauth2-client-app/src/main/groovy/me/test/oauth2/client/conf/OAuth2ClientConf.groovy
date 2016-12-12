package me.test.oauth2.client.conf

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.client.OAuth2ClientContext
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer

/**
 *
 */
@Configuration
@EnableOAuth2Client
public class OAuth2ClientConf  {


    @Value('${my.oauth2.resource.id}')
    private String resourceId;

    @Value('${my.oauth2.resource.accessTokenUri}')
    private String accessTokenUri

    @Value('${my.oauth2.resource.userAuthorizationUri}')
    private String userAuthorizationUri


    @Value('${my.oauth2.client.id}')
    private String clientId;

    @Value('${my.oauth2.client.secret}')
    private String clientSecret;

    @Bean
    public OAuth2ProtectedResourceDetails myRscResourceDetails() {
        AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
        details.setId(resourceId);
        details.setClientId(clientId);
        details.setClientSecret("secret");
        details.setAccessTokenUri(accessTokenUri);
        details.setUserAuthorizationUri(userAuthorizationUri);
        details.setScope(Arrays.asList("read", "write"));
        return details;
    }

    @Bean
    public OAuth2RestTemplate myRscRestTemplate(OAuth2ClientContext clientContext) {
        return new OAuth2RestTemplate(myRscResourceDetails(), clientContext);
    }

}















