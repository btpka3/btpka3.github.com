package me.test.oauth2.clientSso.conf

import me.test.oauth2.common.MyOAuth2Properties
import org.springframework.beans.factory.ObjectProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2SsoProperties
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateCustomizer
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer
import org.springframework.security.oauth2.client.OAuth2ClientContext
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails
import org.springframework.security.oauth2.client.token.AccessTokenProvider
import org.springframework.security.oauth2.provider.token.RemoteTokenServices
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher
import org.springframework.web.accept.ContentNegotiationStrategy
import org.springframework.web.accept.HeaderContentNegotiationStrategy

/**
 *
 */
@Configuration
public class OAuth2SsoConf extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {


    private OAuth2ClientAuthenticationProcessingFilter ssoFilter

    @Autowired
    private MyOAuth2Properties myOAuth2Props

    @Autowired
    OAuth2SsoProperties sso

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
    // EnableOAuth2Sso -> ResourceServerTokenServicesConfiguration 有类似配置
    RemoteTokenServices myRemoteTokenServices(RestTemplateBuilder restTemplateBuilder) {
        RemoteTokenServices ts = new RemoteTokenServices()
        ts.setCheckTokenEndpointUrl(myOAuth2Props.auth.checkTokenUri);
        ts.setClientId(myOAuth2Props.resource.username4AuthServer);
        ts.setClientSecret(myOAuth2Props.resource.password4AuthServer);
        // 内部默认自己新建的，为了方便跟踪调试，统一使用自己配置的。
        ts.setRestTemplate(restTemplateBuilder.build())
        return ts
    }


    @Bean
    OAuth2SsoProperties oauth2SsoProperties() {
        return new OAuth2SsoProperties()
    }

    @Bean
    SavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler() {
        def authenticationSuccessHandler = new SavedRequestAwareAuthenticationSuccessHandler()
        authenticationSuccessHandler.setTargetUrlParameter("target_url")
        return authenticationSuccessHandler
    }

    @Autowired
    void oauth2ClientAuthenticationProcessingFilter(
            UserInfoRestTemplateFactory userInfoRestTemplateFactory,
            ResourceServerTokenServices tokenServices,
            SavedRequestAwareAuthenticationSuccessHandler authenticationSuccessHandler,
            OAuth2SsoProperties sso,
            ApplicationContext applicationContext
    ) {
        ssoFilter = new OAuth2ClientAuthenticationProcessingFilter(sso.getLoginPath());
        ssoFilter.setRestTemplate(userInfoRestTemplateFactory.getUserInfoRestTemplate());
        ssoFilter.setTokenServices(tokenServices);
        ssoFilter.setApplicationEventPublisher(applicationContext);
        ssoFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler)
    }


    @Override
    public void init(HttpSecurity http) throws Exception {
        super.init(http)
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        addSsoFilter(http)
        addAuthenticationEntryPoint(http)
    }


    private void addSsoFilter(HttpSecurity http) {
        ssoFilter.setSessionAuthenticationStrategy(
                http.getSharedObject(SessionAuthenticationStrategy.class));
        http.addFilterAfter(ssoFilter, AbstractPreAuthenticatedProcessingFilter.class);
    }

    // copy from SsoSecurityConfigurer
    private void addAuthenticationEntryPoint(HttpSecurity http)
            throws Exception {
        ExceptionHandlingConfigurer<HttpSecurity> exceptions = http.exceptionHandling();
        ContentNegotiationStrategy contentNegotiationStrategy = http
                .getSharedObject(ContentNegotiationStrategy.class);
        if (contentNegotiationStrategy == null) {
            contentNegotiationStrategy = new HeaderContentNegotiationStrategy();
        }
        MediaTypeRequestMatcher preferredMatcher = new MediaTypeRequestMatcher(
                contentNegotiationStrategy, MediaType.APPLICATION_XHTML_XML,
                new MediaType("image", "*"), MediaType.TEXT_HTML, MediaType.TEXT_PLAIN);
        preferredMatcher.setIgnoredMediaTypes(Collections.singleton(MediaType.ALL));
        exceptions.defaultAuthenticationEntryPointFor(
                new LoginUrlAuthenticationEntryPoint(sso.getLoginPath()),
                preferredMatcher);
        // When multiple entry points are provided the default is the first one
        exceptions.defaultAuthenticationEntryPointFor(
                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                new RequestHeaderRequestMatcher("X-Requested-With", "XMLHttpRequest"));
    }
}