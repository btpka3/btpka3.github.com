package me.test.oauth2.common

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "my.oauth2")
class MyOAuth2Properties {

    Auth auth = new Auth()
    Rsc resource = new Rsc()
    Client client = new Client()


    static class Auth {
        String url
        String accessTokenUri
        String userAuthorizationUri
        String checkTokenUri
        String realm
    }

    static class Rsc {
        String id
        String url
        String photoListUri
        // 验证 token 有效性时，去访问 auth server `/oauth/check_token` 时使用的用户名和密码
        String username4AuthServer
        String password4AuthServer
    }

    static class Client {
        String id
        String secret
        String url
        String[] scopes
        String[] authorizedGrantTypes
        String[] authorities
        String preEstablishedRedirectUri
    }
}
