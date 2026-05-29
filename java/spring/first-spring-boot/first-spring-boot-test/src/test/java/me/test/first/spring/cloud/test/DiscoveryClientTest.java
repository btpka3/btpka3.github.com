package me.test.first.spring.cloud.test;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.composite.CompositeDiscoveryClient;
import org.springframework.cloud.client.discovery.event.HeartbeatEvent;
import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClient;
import org.springframework.context.event.EventListener;

import java.util.List;

/**
 *
 * @author dangqian.zll
 * @date 2026/5/9
 * @see DiscoveryClient
 * @see CompositeDiscoveryClient
 * @see SimpleDiscoveryClient
 * @see HeartbeatEvent
 */
public class DiscoveryClientTest {

    /// 服务实例变化时触发（新增、下线、心跳续约）
    /// HeartbeatEvent#getValue() :
    /// - nacos : 心跳计数器（Long 类型，递增数字）
    /// - ZooKeeper : 变更时间戳（Long）
    /// - Consul : 变更索引（Long）
    /// - Eureka : 增量数据哈希或时间戳
    @EventListener
    public void x(HeartbeatEvent e) {
        DiscoveryClient discoveryClient = null;
        // 获取当前所有 serviceId
        List<String> serviceIds = discoveryClient.getServices();

        // 获取某服务的实例列表
        List<ServiceInstance> instances = discoveryClient.getInstances("mtee3-cluster");
    }
}
