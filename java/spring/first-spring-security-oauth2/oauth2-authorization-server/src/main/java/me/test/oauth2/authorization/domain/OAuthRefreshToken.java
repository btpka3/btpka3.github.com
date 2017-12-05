package me.test.oauth2.authorization.domain;

import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

/**
 * 演示用 Domain
 * <p>
 * 字段允许的类型：java基础数据类型以及其封装类型。
 *
 */
@Document
public class OAuthRefreshToken extends Base {

/*
-- https://github.com/spring-projects/spring-security-oauth/blob/2.1.0.RELEASE/spring-security-oauth2/src/test/resources/schema.sql

create table oauth_refresh_token (
  token_id VARCHAR(256),
  token LONGVARBINARY,
  authentication LONGVARBINARY
);

*/
    // ------------------------ 业务字段

    @Indexed(unique = true)
    private String tokenId;
    private byte[] token;
    private byte[] authentication;

    // ------------------------ 自动生成的 getter、 setter


    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public byte[] getToken() {
        return token;
    }

    public void setToken(byte[] token) {
        this.token = token;
    }

    public byte[] getAuthentication() {
        return authentication;
    }

    public void setAuthentication(byte[] authentication) {
        this.authentication = authentication;
    }
}
