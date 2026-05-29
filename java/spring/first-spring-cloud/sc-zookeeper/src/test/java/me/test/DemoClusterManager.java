package me.test;

import jakarta.annotation.PostConstruct;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.event.HeartbeatEvent;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.zookeeper.discovery.ZookeeperInstance;
import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperServiceRegistry;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author dangqian.zll
 * @date 2026/5/9
 * @see ZookeeperServiceRegistry#register 写入 ZK：/services/mtee3-cluster/{UUID} = 临时节点
 */
@Service
public class DemoClusterManager {

    @Autowired
    private DiscoveryClient discoveryClient;

    private volatile List<String> currentAliveHosts = Collections.emptyList();

    @PostConstruct
    public void init() {
        // 启动时获取一次
        currentAliveHosts = getAllAliveHostsByCluster("mtee3-cluster");
    }

    // 你的目标接口
    public List<String> getAllAliveHostsByCluster(String clusterName) {
        return discoveryClient.getInstances(clusterName).stream()
                .map(si -> si.getHost() + ":" + si.getPort())
                .sorted()  // 排序保证各节点看到的列表一致
                .collect(Collectors.toList());
    }

    // 监听集群变化，触发重平衡
    @EventListener(HeartbeatEvent.class)
    public void onClusterChange(HeartbeatEvent event) {
        List<String> newHosts = getAllAliveHostsByCluster("mtee3-cluster");
        if (!newHosts.equals(currentAliveHosts)) {
            currentAliveHosts = newHosts;
            rebalanceDatabaseTasks(newHosts);
        }
    }

    private void rebalanceDatabaseTasks(List<String> aliveHosts) {
        // 一致性哈希 / Range 划分 / 工作窃取
        // 实现最终一致性的任务平分
    }


}