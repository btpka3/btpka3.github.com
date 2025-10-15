package me.test;

/**
 *
 * @author dangqian.zll
 * @date 2025/9/23
 */
@BoostrapConfiguration
public class DefaultCuratorFrameworkConfig {
    public ZookeeperConfig(CuratorFramework curator) {
        curator.addAuthInfo("digest", "user:password".getBytes());
    }
}
