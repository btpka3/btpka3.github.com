package me.test.first.spring.boot.test;

import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.spring.starter.embedded.InfinispanCacheConfigurer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @date 2019-03-12
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = InfinispanTest.Conf.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@TestPropertySource
public class InfinispanTest {


    @SpringBootApplication
    @EnableCaching
    @EnableScheduling
    public static class Conf {

        @Bean
        public InfinispanCacheConfigurer cacheConfigurer() {
            return manager -> {
                final org.infinispan.configuration.cache.Configuration ispnConfig = new ConfigurationBuilder()
                        .clustering()
                        .cacheMode(CacheMode.LOCAL)
                        .build();

                manager.defineConfiguration("local-sync-config", ispnConfig);
            };
        }

    }

    @Autowired
    EmbeddedCacheManager cacheManager;

    @Test
    public void test01() {
        cacheManager.getCache("testCache1").put("testKey", "testValue");
        System.out.println("Received value from cache: " + cacheManager.getCache("testCache1").get("testKey"));
    }
}
