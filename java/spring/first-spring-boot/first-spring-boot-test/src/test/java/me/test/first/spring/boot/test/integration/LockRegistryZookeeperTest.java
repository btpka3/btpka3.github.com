package me.test.first.spring.boot.test.integration;

import lombok.SneakyThrows;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.integration.zookeeper.config.CuratorFrameworkFactoryBean;
import org.springframework.integration.zookeeper.lock.ZookeeperLockRegistry;
import org.springframework.integration.zookeeper.metadata.ZookeeperMetadataStore;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author dangqian.zll
 * @date 2024/9/4
 */
@SpringBootTest
@Import({
        LockRegistryZookeeperTest.Conf.class
})
public class LockRegistryZookeeperTest {

    @Configuration
    public static class Conf {
        @Bean
        public CuratorFrameworkFactoryBean client(
                @Value("${spring.zookeeper.connect-string}") String connectString
        ) {
            return new CuratorFrameworkFactoryBean(connectString);
        }

        @Bean
        public ZookeeperMetadataStore client(
                CuratorFramework client
        ) {
            return new ZookeeperMetadataStore(client);
        }

        @Bean
        public ZookeeperLockRegistry lockRegistry(
                CuratorFramework client
        ) {
            ZookeeperLockRegistry lockRegistry = new ZookeeperLockRegistry(client);
            return lockRegistry;
        }

    }

    @Autowired
    LockRegistry lockRegistry;

    @SneakyThrows
    public void test01() {

        Lock lock = lockRegistry.obtain("test");
        if (lock.tryLock(1, TimeUnit.SECONDS)) {
            try {
                System.out.println("get lock");
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("get lock failed");
        }
    }
}
