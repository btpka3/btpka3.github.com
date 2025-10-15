package me.test;

import org.apache.curator.framework.CuratorFramework;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.zookeeper.ZookeeperProperties;
import org.springframework.cloud.zookeeper.discovery.dependency.ZookeeperDependencies;
import org.springframework.cloud.zookeeper.discovery.watcher.DependencyWatcherListener;
import org.springframework.cloud.zookeeper.serviceregistry.ServiceInstanceRegistration;
import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperRegistration;
import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperServiceRegistry;

/**
 *
 * @author dangqian.zll
 * @date 2025/9/23
 * @see ZookeeperProperties
 * @see ZookeeperDependencies
 * @see DependencyWatcherListener
 */
public class Aaa {

    public CuratorFramework curatorFramework() {
        CuratorFramework curator = null;
        curator.addAuthInfo("digest", "user:password".getBytes());


        return curator;
    }

    @Test
    public void testServiceRegistry() {
        //LoadBalancer

        ZookeeperRegistration registration = ServiceInstanceRegistration.builder()
                .defaultUriSpec()
                .address("anyUrl")
                .port(10)
                .name("/a/b/c/d/anotherservice")
                .build();

        ZookeeperServiceRegistry reg = null;
        reg.register(registration);
    }
}
