package me.test.oauth2.client.conf

import me.test.oauth2.client.MyOAuth2Properties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.OAuth2ClientContext
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client

/**
 *
 */
@Configuration
@EnableOAuth2Client
public class OAuth2ClientConf {

    @Autowired
    private MyOAuth2Properties myOAuth2Props

    @Bean
    MyOAuth2Properties myOAuth2Properties() {
        return new MyOAuth2Properties()
    }

    @Bean
    public OAuth2ProtectedResourceDetails myRscResourceDetails() {
        AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
        details.setId(myOAuth2Props.resource.id);
        details.setClientId(myOAuth2Props.client.id);
        details.setClientSecret(myOAuth2Props.client.secret);
        details.setAccessTokenUri(myOAuth2Props.auth.accessTokenUri);
        details.setUserAuthorizationUri(myOAuth2Props.auth.userAuthorizationUri);
        details.setScope(Arrays.asList(myOAuth2Props.client.scopes));
        return details;
    }

    @Bean
    public OAuth2RestTemplate myRscRestTemplate(OAuth2ClientContext clientContext) {
        return new OAuth2RestTemplate(myRscResourceDetails(), clientContext);
    }

}















