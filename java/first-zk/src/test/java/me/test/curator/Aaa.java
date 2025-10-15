package me.test.curator;

import lombok.SneakyThrows;
import org.apache.curator.CuratorZookeeperClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.client.ZKClientConfig;

/**
 *
 * @author dangqian.zll
 * @date 2025/9/24
 */
public class Aaa {

    @SneakyThrows
    public void xxx() {
        ZKClientConfig zkClientConfig = new ZKClientConfig();
        CuratorFramework curator = CuratorFrameworkFactory.builder()
                .connectString("host")
                .retryPolicy(new RetryNTimes(3, 1000))
                .connectionTimeoutMs(1000)
                .sessionTimeoutMs(10000)
                .zkClientConfig(zkClientConfig)
                //.authorization()
                // etc. etc.
                .build();
        curator.start();
        // CuratorFramework => zooKeeper
        CuratorZookeeperClient curatorZookeeperClient = curator.getZookeeperClient();

        ZooKeeper zooKeeper = curatorZookeeperClient.getZooKeeper();


        ZKClientConfig zkClientConfig1 = zooKeeper.getClientConfig();

        curator.close();
    }

    public void xx(){
        CuratorFramework curator = null;
    }
}
