package me.test.oauth2.common;

import org.springframework.boot.context.properties.*;

@ConfigurationProperties(prefix = "my.oauth2")
class MyOAuth2Properties {

    private Auth auth;
    private Rsc resource;
    private Client client;


    public static class Auth {
        private String url;
        private String accessTokenUri;
        private String userAuthorizationUri;
        private String checkTokenUri;
        private String realm;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAccessTokenUri() {
            return accessTokenUri;
        }

        public void setAccessTokenUri(String accessTokenUri) {
            this.accessTokenUri = accessTokenUri;
        }

        public String getUserAuthorizationUri() {
            return userAuthorizationUri;
        }

        public void setUserAuthorizationUri(String userAuthorizationUri) {
            this.userAuthorizationUri = userAuthorizationUri;
        }

        public String getCheckTokenUri() {
            return checkTokenUri;
        }

        public void setCheckTokenUri(String checkTokenUri) {
            this.checkTokenUri = checkTokenUri;
        }

        public String getRealm() {
            return realm;
        }

        public void setRealm(String realm) {
            this.realm = realm;
        }
    }

    public static class Rsc {
        private String id;
        private String url;
        private String photoListUri;
        // 验证 token 有效性时，去访问 auth server `/oauth/check_token` 时使用的用户名和密码
        private String username4AuthServer;
        private String password4AuthServer;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPhotoListUri() {
            return photoListUri;
        }

        public void setPhotoListUri(String photoListUri) {
            this.photoListUri = photoListUri;
        }

        public String getUsername4AuthServer() {
            return username4AuthServer;
        }

        public void setUsername4AuthServer(String username4AuthServer) {
            this.username4AuthServer = username4AuthServer;
        }

        public String getPassword4AuthServer() {
            return password4AuthServer;
        }

        public void setPassword4AuthServer(String password4AuthServer) {
            this.password4AuthServer = password4AuthServer;
        }
    }

    public static class Client {
        private String id;
        private String secret;
        private String url;
        private String[] scopes;
        private String[] authorizedGrantTypes;
        private String[] authorities;
        private String preEstablishedRedirectUri;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String[] getScopes() {
            return scopes;
        }

        public void setScopes(String[] scopes) {
            this.scopes = scopes;
        }

        public String[] getAuthorizedGrantTypes() {
            return authorizedGrantTypes;
        }

        public void setAuthorizedGrantTypes(String[] authorizedGrantTypes) {
            this.authorizedGrantTypes = authorizedGrantTypes;
        }

        public String[] getAuthorities() {
            return authorities;
        }

        public void setAuthorities(String[] authorities) {
            this.authorities = authorities;
        }

        public String getPreEstablishedRedirectUri() {
            return preEstablishedRedirectUri;
        }

        public void setPreEstablishedRedirectUri(String preEstablishedRedirectUri) {
            this.preEstablishedRedirectUri = preEstablishedRedirectUri;
        }
    }

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    public Rsc getResource() {
        return resource;
    }

    public void setResource(Rsc resource) {
        this.resource = resource;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
