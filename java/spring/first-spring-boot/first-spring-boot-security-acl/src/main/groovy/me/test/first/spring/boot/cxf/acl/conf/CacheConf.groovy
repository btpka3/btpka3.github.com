package me.test.first.spring.boot.cxf.acl.conf

import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * 在 application.yml 中通过 `spring.cache.type` 指定 cache 类型。
 *
 * 该示例中不使用 cache，故下面的配置属于无效代码，做备注用。
 */
@Configuration
@EnableCaching
class CacheConf {

    @Bean
    public CacheManagerCustomizer<ConcurrentMapCacheManager> cacheManagerCustomizer() {
        return new CacheManagerCustomizer<ConcurrentMapCacheManager>() {
            @Override
            public void customize(ConcurrentMapCacheManager cacheManager) {
                // NoOpCacheConfiguration
                // Do nothing.
            }
        };
    }
}
