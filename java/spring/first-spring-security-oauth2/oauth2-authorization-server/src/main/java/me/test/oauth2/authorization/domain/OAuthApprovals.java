package me.test.oauth2.authorization.domain;

import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.*;

/**
 * 演示用 Domain
 * <p>
 * 字段允许的类型：java基础数据类型以及其封装类型。
 *
 */
@Document
@CompoundIndexes({
        @CompoundIndex(def = "{userId:1,clientId:1,scope:1}", unique = true)
})
public class OAuthApprovals extends Base {


/*
-- https://github.com/spring-projects/spring-security-oauth/blob/2.1.0.RELEASE/spring-security-oauth2/src/test/resources/schema.sql

create table oauth_approvals (
    userId VARCHAR(256),
    clientId VARCHAR(256),
    scope VARCHAR(256),
    status VARCHAR(10),
    expiresAt TIMESTAMP,
    lastModifiedAt TIMESTAMP
);

*/

    private String userId;
    private String clientId;
    private String scope;

    private String status;

    // 父类中的由框架自动处理。这里的由业务处理。
    private Date lastUpdatedAt;

    // 过期后仍多保留 1 天
    @Indexed(expireAfterSeconds = 1 * 24 * 60 * 60)
    private Date expiresAt;


    // ------------------------ 自动生成的 getter、 setter

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(Date lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }
}
