package me.test.acl.conf

import org.springframework.cache.CacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.acls.AclPermissionEvaluator
import org.springframework.security.acls.domain.*
import org.springframework.security.acls.jdbc.BasicLookupStrategy
import org.springframework.security.acls.jdbc.JdbcMutableAclService
import org.springframework.security.acls.jdbc.LookupStrategy
import org.springframework.security.acls.model.AclCache
import org.springframework.security.acls.model.AclService
import org.springframework.security.acls.model.PermissionGrantingStrategy
import org.springframework.security.core.authority.SimpleGrantedAuthority

import javax.sql.DataSource

@Configuration
class SecAclConf {

    @Bean
    PermissionGrantingStrategy permissionGrantingStrategy() {
        return new DefaultPermissionGrantingStrategy(new ConsoleAuditLogger())
    }

    // FIXME : 这个的作用是？
    @Bean
    AclAuthorizationStrategy aclAuthorizationStrategy() {
        return new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("USER"));
    }

    @Bean
    AclCache aclCache(CacheManager cacheManager,
                      PermissionGrantingStrategy permissionGrantingStrategy,
                      AclAuthorizationStrategy aclAuthorizationStrategy) {
        return new SpringCacheBasedAclCache(
                cacheManager.getCache("myAclCache"),
                permissionGrantingStrategy,
                aclAuthorizationStrategy
        )
    }

    @Bean
    LookupStrategy lookupStrategy(DataSource dataSource,
                                  AclCache aclCache,
                                  AclAuthorizationStrategy aclAuthorizationStrategy,
                                  PermissionGrantingStrategy permissionGrantingStrategy) {

        return new BasicLookupStrategy(
                dataSource,
                aclCache,
                aclAuthorizationStrategy,
                permissionGrantingStrategy
        )
    }

    @Bean
    AclService aclService(DataSource dataSource,
                          LookupStrategy lookupStrategy,
                          AclCache aclCache) {
        return new JdbcMutableAclService(dataSource, lookupStrategy, aclCache)
    }

    @Bean
    PermissionEvaluator permissionEvaluator(AclService aclService) {
        return new AclPermissionEvaluator(aclService)
    }

}