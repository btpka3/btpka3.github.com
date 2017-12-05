package me.test.oauth2.authorization.domain;

import org.springframework.data.mongodb.core.mapping.*;

/**
 * 演示用 Domain
 * <p>
 * 字段允许的类型：java基础数据类型以及其封装类型。
 *
 * https://github.com/spring-projects/spring-security-oauth/blob/2.1.0.RELEASE/spring-security-oauth/src/test/resources/schema.sql
 *
 */
@Document
public class OAuthClientToken extends Base {

/*
-- https://github.com/spring-projects/spring-security-oauth/blob/2.1.0.RELEASE/spring-security-oauth2/src/test/resources/schema.sql

create table oauth_client_token (
  token_id VARCHAR(256),
  token LONGVARBINARY,
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256)
);
*/
    /**
     *
     */
    private String token;

    private String authenticationId;

    private String userName;

    private String clientId;

    // ------------------------ 自动生成的 getter、 setter

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAuthenticationId() {
        return authenticationId;
    }

    public void setAuthenticationId(String authenticationId) {
        this.authenticationId = authenticationId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
