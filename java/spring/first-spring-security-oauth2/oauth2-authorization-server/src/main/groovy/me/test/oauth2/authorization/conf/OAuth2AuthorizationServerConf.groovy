package me.test.oauth2.authorization.conf

import me.test.oauth2.authorization.MyOAuth2Properties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.*
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.security.oauth2.provider.approval.ApprovalStore
import org.springframework.security.oauth2.provider.approval.DefaultUserApprovalHandler
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore

/**
 *
 * 通过 AuthorizationServerSecurityConfiguration 仅仅过滤以下 URL:
 *      /oauth/token
 *      /oauth/token_key
 *      /oauth/check_token
 *
 * 注意 :  虽然 OAuth2AuthorizationServerConfiguration 可提供默认的配置，但是有以下缺陷：
 *      1. 默认只能从 application.yml 中获取一个 client 身份信息，就意味着只能有一个第三方应用。
 * 故不要使用，自己明确提供 AuthorizationServerConfigurer/AuthorizationServerConfigurerAdapter
 */
@Configuration
@EnableAuthorizationServer
//@EnableConfigurationProperties(AuthorizationServerProperties.class)
public class OAuth2AuthorizationServerConf extends AuthorizationServerConfigurerAdapter {

    public static final String MY_RESOURCE_ID = "MY_RSC";

    // -------------------------- 定义 spring beans

    @Bean
    MyOAuth2Properties myOAuth2Properties() {
        return new MyOAuth2Properties()
    }


    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        return new JwtAccessTokenConverter()
    }

    @Bean
    public TokenStore jwtTokenStore() {
        TokenStore tokenStore = new JwtTokenStore(jwtAccessTokenConverter())
        return tokenStore
    }

//    @Bean
//    public TokenStore tokenStore() {
//        return new InMemoryTokenStore();
//    }

    @Bean
    public ApprovalStore approvalStore(TokenStore tokenStore) {
        TokenApprovalStore store = new TokenApprovalStore();
        store.setTokenStore(tokenStore);
        return store;
    }

    @Bean
    @Lazy
    @Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
    public UserApprovalHandler userApprovalHandler() throws Exception {
//        SparklrUserApprovalHandler handler = new SparklrUserApprovalHandler();
//        handler.setApprovalStore(approvalStore());
//        handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
//        handler.setClientDetailsService(clientDetailsService);
//        handler.setUseApprovalStore(true);
//        return handler;

//        TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
//        handler.setClientDetailsService(null);
//        handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
//        handler.setClientDetailsService(clientDetailsService);
//        return handler;

        DefaultUserApprovalHandler handler = new DefaultUserApprovalHandler();
        return handler;

    }

    // --------------------------
//    @Autowired
//    private AuthorizationServerProperties properties;
    @Autowired
    private MyOAuth2Properties myOAuth2Props

    @Autowired
    private ClientDetailsService clientDetailsService;


    @Autowired
    private UserApprovalHandler userApprovalHandler;


    @Autowired
//    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager; // 人：Resource Owner

    @Value('${tonr.redirect:http://localhost:8080/tonr2/sparklr/redirect}')
    //@Value('${tonr.redirect}')
    private String tonrRedirectUri;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        clients.inMemory()

                .withClient(myOAuth2Props.client.id)
                .resourceIds(myOAuth2Props.resource.id)
                .authorizedGrantTypes(myOAuth2Props.client.authorizedGrantTypes)
                .authorities(myOAuth2Props.client.authorities)
                .scopes(myOAuth2Props.client.scopes)
                .secret(myOAuth2Props.client.secret)

//                .withClient("tonr")
//                .resourceIds(MY_RESOURCE_ID)
//                .authorizedGrantTypes("authorization_code", "implicit")
//                .authorities("ROLE_CLIENT")
//                .scopes("read", "write")
//                .secret("secret")
//
//                .and()
//                .withClient("tonr-with-redirect")
//                .resourceIds(MY_RESOURCE_ID)
//                .authorizedGrantTypes("authorization_code", "implicit")
//                .authorities("ROLE_CLIENT")
//                .scopes("read", "write")
//                .secret("secret")
//                .redirectUris(tonrRedirectUri)
//
//                .and()
//                .withClient("my-client-with-registered-redirect")
//                .resourceIds(MY_RESOURCE_ID)
//                .authorizedGrantTypes("authorization_code", "client_credentials")
//                .authorities("ROLE_CLIENT")
//                .scopes("read", "trust")
//                .redirectUris("http://anywhere?key=value")
//
//                .and()
//                .withClient("my-trusted-client")
//                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
//                .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
//                .scopes("read", "write", "trust")
//                .accessTokenValiditySeconds(60)
//
//                .and()
//                .withClient("my-trusted-client-with-secret")
//                .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
//                .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
//                .scopes("read", "write", "trust")
//                .secret("somesecret")
//
//                .and()
//                .withClient("my-less-trusted-client")
//                .authorizedGrantTypes("authorization_code", "implicit")
//                .authorities("ROLE_CLIENT")
//                .scopes("read", "write", "trust")
//
//                .and()
//                .withClient("my-less-trusted-autoapprove-client")
//                .authorizedGrantTypes("implicit")
//                .authorities("ROLE_CLIENT")
//                .scopes("read", "write", "trust")
//                .autoApprove(true);
    }

    // AuthorizationServerEndpointsConfiguration#authorizationEndpoint() 已经配置好了
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.accessTokenConverter(jwtAccessTokenConverter())
                .tokenStore(jwtTokenStore())
        //.userApprovalHandler(userApprovalHandler)
        //.authenticationManager(authenticationManager);
        //.pathMapping("/oauth/confirm_access","/your/controller") // 可以修改映射路径。
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {

        // 通过 application.yml : "security.oauth2.authorization.realm"
        oauthServer.realm("btpka3/oauth2");
    }

}















