package me.test.oauth2.authorization.domain;

import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.*;

/**
 * 演示用 Domain
 * <p>
 * 字段允许的类型：java基础数据类型以及其封装类型。
 *
 * @see org.springframework.security.oauth2.provider.ClientDetails
 * @see org.springframework.security.oauth2.provider.ClientDetailsService
 */
@Document
public class OAuthClientDetails extends Base {

/*
-- https://github.com/spring-projects/spring-security-oauth/blob/2.1.0.RELEASE/spring-security-oauth2/src/test/resources/schema.sql

create table oauth_client_details (
  client_id VARCHAR(256) PRIMARY KEY,
  resource_ids VARCHAR(256),
  client_secret VARCHAR(256),
  scope VARCHAR(256),
  authorized_grant_types VARCHAR(256),
  web_server_redirect_uri VARCHAR(256),
  authorities VARCHAR(256),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(256)
);
*/

    /**
     * 客户端ID
     */
    @Indexed(unique = true )
    private String clientId;

    /**
     * 该客户端可以访问的 resource 的 id 列表。
     */
    private Set<String> resourceIds;

    /**
     * 客户端密码
     */
    private String clientSecret;

    /**
     * 客户端允许访问的 scope 。
     */
    private Set<String> scope;

    /**
     * 该客户端被可以通过哪种方式完成授权。
     */
    private Set<String> authorizedGrantTypes;

    /**
     * 如果授权类型是 'authorization_code' 时，该客户端登记的跳转 URL。
     */
    private Set<String> registeredRedirectUris;

    /**
     * 授权给该客户端的权限。
     */
    private Set<String> authorities;

    /**
     * 分配给该客户端的 Access token 的有效时间（单位：秒）。
     * <p>
     * 如果为 null，则暗含着使用默认的时长。
     */
    private Integer accessTokenValidity;

    /**
     * 分配给该客户端的 Refresh token 的有效时长（单位：秒）。
     * <p>
     * 如果为 null，则使用默认值。
     * 如果为 0 或者 负值，则代表永不过期。
     */
    private Integer refreshTokenValidity;

    /**
     * 该客户端自动授权的 scope。
     */
    private Set<String> autoApproveScopes;

    /**
     * 该客户端额外的信息.
     */
    private Map<String, Object> additionalInformation;


    // ------------------------ 自动生成的 getter、 setter

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Set<String> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(Set<String> resourceIds) {
        this.resourceIds = resourceIds;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public Set<String> getScope() {
        return scope;
    }

    public void setScope(Set<String> scope) {
        this.scope = scope;
    }

    public Set<String> getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public Set<String> getRegisteredRedirectUris() {
        return registeredRedirectUris;
    }

    public void setRegisteredRedirectUris(Set<String> registeredRedirectUris) {
        this.registeredRedirectUris = registeredRedirectUris;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public Integer getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(Integer accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public Integer getRefreshTokenValidity() {
        return refreshTokenValidity;
    }

    public void setRefreshTokenValidity(Integer refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }

    public Set<String> getAutoApproveScopes() {
        return autoApproveScopes;
    }

    public void setAutoApproveScopes(Set<String> autoApproveScopes) {
        this.autoApproveScopes = autoApproveScopes;
    }

    public Map<String, Object> getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(Map<String, Object> additionalInformation) {
        this.additionalInformation = additionalInformation;
    }
}
