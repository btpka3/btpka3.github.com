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
public class OAuthCode extends Base {

/*
-- https://github.com/spring-projects/spring-security-oauth/blob/2.1.0.RELEASE/spring-security-oauth2/src/test/resources/schema.sql

create table oauth_code (
  code VARCHAR(256), authentication LONGVARBINARY
);

*/

    @Indexed(unique = true)
    private String code;
    private byte[] authentication;

    // ------------------------ 自动生成的 getter、 setter


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte[] getAuthentication() {
        return authentication;
    }

    public void setAuthentication(byte[] authentication) {
        this.authentication = authentication;
    }
}
