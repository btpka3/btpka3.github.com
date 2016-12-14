package me.test.oauth2.client

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "my.oauth2")
class MyOAuth2Properties {
    Auth auth = new Auth()
    Rsc resource = new Rsc()
    Client client = new Client()


    static class Auth {
        int port
        String accessTokenUri
        String userAuthorizationUri
    }

    static class Rsc {
        String id
        int port
        String photoListUri
        String photoDetailUri
    }

    static class Client {
        String id
        String secret
        String[] scopes
        String[] authorizedGrantTypes
        String[] authorities
    }
}
