package me.test.oauth2.clientSso.conf

import me.test.oauth2.common.MyOAuth2Properties
import org.springframework.beans.factory.ObjectProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateCustomizer
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.oauth2.client.OAuth2ClientContext
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails
import org.springframework.security.oauth2.client.token.AccessTokenProvider
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitAccessTokenProvider
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider
import org.springframework.security.oauth2.http.converter.FormOAuth2AccessTokenMessageConverter
import org.springframework.security.oauth2.http.converter.FormOAuth2ExceptionHttpMessageConverter
import org.springframework.security.oauth2.provider.token.RemoteTokenServices
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.web.client.RestOperations
import org.springframework.web.client.RestTemplate

/**
 *
 */
@Configuration
public class OAuth2SsoConf {

    @Autowired
    private MyOAuth2Properties myOAuth2Props

    @Bean
    UserInfoRestTemplateCustomizer userInfoRestTemplateCustomizer(AccessTokenProvider accessTokenProvider) {
        UserInfoRestTemplateCustomizer restTemplateCustomizer = new UserInfoRestTemplateCustomizer() {
            @Override
            void customize(OAuth2RestTemplate template) {
                template.setAccessTokenProvider(accessTokenProvider)
            }
        }
        return restTemplateCustomizer
    }

    // ResourceServerTokenServicesConfiguration 有自动配置

    @Bean
    public UserInfoRestTemplateFactory userInfoRestTemplateFactory(
            ObjectProvider<List<UserInfoRestTemplateCustomizer>> customizersProvider,
            @Qualifier("oAuthCodeLoginResourceDetails")
            ObjectProvider<OAuth2ProtectedResourceDetails> detailsProvider,
            ObjectProvider<OAuth2ClientContext> oauth2ClientContextProvider) {
        return new UserInfoRestTemplateFactory(customizersProvider, detailsProvider,
                oauth2ClientContextProvider);
    }

    @Bean
    @Primary
    RemoteTokenServices myRemoteTokenServices(RestTemplateBuilder restTemplateBuilder) {
        RemoteTokenServices ts = new RemoteTokenServices()
        ts.setCheckTokenEndpointUrl(myOAuth2Props.auth.checkTokenUri);
        ts.setClientId(myOAuth2Props.resource.username4AuthServer);
        ts.setClientSecret(myOAuth2Props.resource.password4AuthServer);
        // 内部默认自己新建的，为了方便跟踪调试，统一使用自己配置的。
        ts.setRestTemplate(restTemplateBuilder.build())
        return ts
    }
}