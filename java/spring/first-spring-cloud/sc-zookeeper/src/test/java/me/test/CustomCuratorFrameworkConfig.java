package me.test;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.context.annotation.Bean;

/**
 *
 * @author dangqian.zll
 * @date 2025/9/23
 */
@BoostrapConfiguration
public class CustomCuratorFrameworkConfig {
    @Bean
    public CuratorFramework curatorFramework() {
        CuratorFramework curator = new CuratorFramework();
        curator.addAuthInfo("digest", "user:password".getBytes());
        return curator;
    }
}
