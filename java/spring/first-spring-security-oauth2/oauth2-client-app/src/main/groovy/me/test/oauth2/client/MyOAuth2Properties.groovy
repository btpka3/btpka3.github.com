package me.test.oauth2.client

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
