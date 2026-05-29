package me.test.first.spring.cloud.test;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.client.NacosPropertySource;
import com.alibaba.cloud.nacos.discovery.NacosDiscoveryClient;
import com.alibaba.cloud.nacos.discovery.NacosDiscoveryHeartBeatPublisher;
import com.alibaba.cloud.nacos.discovery.NacosWatch;
import com.alibaba.cloud.nacos.registry.NacosServiceRegistry;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.common.remote.client.grpc.GrpcClient;
import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import com.alibaba.nacos.spring.context.annotation.discovery.EnableNacosDiscovery;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * nacos 是由 NacosDiscoveryHeartBeatPublisher每 30 秒定时发布，不是由服务端变更推送触发的 HeartbeatEvent
 * <p>
 * HeartbeatEvent.source == NacosDiscoveryHeartBeatPublisher
 * HeartbeatEvent.value 是 是递增的心跳计数器
 *
 * @author dangqian.zll
 * @date 2026/5/9
 * @see NacosPropertySource
 * @see NacosValue
 * @see EnableNacosDiscovery
 * @see EnableNacosConfig
 * @see NamingService
 * @see NacosDiscoveryClient
 * @see NacosServiceRegistry
 * @see NacosDiscoveryHeartBeatPublisher
 * @see NacosWatch
 */
@Slf4j
public class NacosTest {

    @Bean
    public NacosDiscoveryProperties nacosDiscoveryProperties() {
        NacosDiscoveryProperties properties = new NacosDiscoveryProperties();
        properties.setServerAddr("serverAddr");
        properties.setUsername("username");
        properties.setPassword("password");
        properties.setNamespace("namespace");
        return properties;
    }

    @SneakyThrows
    public void x() {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, "127.0.0.1:8848");
        properties.setProperty(PropertyKeyConst.NAMESPACE, "your-namespace");
        // 认证相关配置
        properties.setProperty(PropertyKeyConst.USERNAME, "nacos");
        properties.setProperty(PropertyKeyConst.PASSWORD, "nacos-password");

        NamingService namingService = NacosFactory.createNamingService(properties);

        String serviceName = "g9:survival:${aone宿主应用}:{九宫集群code}";

        Instance instance = new Instance();
        instance.setInstanceId("SN_xxx");
        instance.setIp("111.222.333.444");
        instance.setPort(-1);
        instance.setWeight(1.0D);
        // healthy : 这里与 健康检查、服务上下线、k8s liveness/readiness 探针相关
        instance.setHealthy(true);
        instance.setEnabled(true);
        // ephemeral==true: 短时的，基于心跳等机制 自动标注服务不可用。
        instance.setEphemeral(true);
        instance.setClusterName("九宫的逻辑集群code_xxx");
        instance.setServiceName(serviceName);
        Map<String, String> metadata = new HashMap<>(16);
        metadata.put("cluster", "九宫的逻辑集群code_xxx");
        metadata.put("nodeGroup", "应用分组");
        metadata.put("env", "线上");
        metadata.put("ip", "111.222.333.444");
        metadata.put("sn", "SN_xxx");
        metadata.put("unit", "单元标_xxx");
        metadata.put("machineRoom", "机房_xxx");
        metadata.put("pid", "java进程ID");
        metadata.put("starTime", "java进程启动时间");
        metadata.put("g9AppPubFlowId", "九宫APP发布单ID");
        metadata.put("hot", "预热V2是否是热机器_true");
        instance.setMetadata(metadata);

        // 注意：instance 中任何数据有变更都要重新调用 namingService.registerInstance(Instance) // upsert 操作。
        namingService.registerInstance(instance.getServiceName(), instance);
        //  registerInstance 之后 ：
        //  同client 立即调用 调用 getAllInstances，会0毫秒返回，但返回的是本地缓存的旧数据。
        //  不同 client 则需要 50ms ~ 500ms 才能拿到最新数据。
        List<Instance> s = namingService.getAllInstances("serviceName");

        namingService.subscribe("mtee3-cluster", event -> {
            NamingEvent namingEvent = (NamingEvent) event;

            // 这里直接拿到变更后的实例列表
            List<Instance> allInstances = namingEvent.getInstances();

            List<String> aliveHosts = allInstances.stream()
                    .filter(Instance::isHealthy)
                    .map(i -> i.getIp() + ":" + i.getPort())
                    .collect(Collectors.toList());

            log.info("Nacos 推送，实例列表: {}", aliveHosts);
        });
    }

    @SneakyThrows
    public void ConfigServiceTest() {
        Properties properties = new Properties();
        ConfigService configService = NacosFactory.createConfigService(properties);

    }

    /// 示例nacos java 客户端如何开启 mtls 双向任认证
    ///
    /// @see GrpcClient
    @SneakyThrows
    public void mtlsAUth() {
        Properties properties = new Properties();

        // 启用 TLS
        properties.setProperty("nacos.remote.client.rpc.tls.enable", "true");

        // 开启双向认证（mTLS）
        properties.setProperty("nacos.remote.client.rpc.tls.mutualAuth", "true");

        // 客户端证书链（PEM 格式）
        properties.setProperty("nacos.remote.client.rpc.tls.certChainFile", "/path/to/client-cert.pem");

        // 客户端私钥（PEM 格式）
        properties.setProperty("nacos.remote.client.rpc.tls.certPrivateKey", "/path/to/client-key.pem");

        // 私钥密码（如果有）
        properties.setProperty("nacos.remote.client.rpc.tls.certPrivateKeyPassword", "your-key-password");

        // 服务端 CA 证书（用于验证服务端身份）
        properties.setProperty("nacos.remote.client.rpc.tls.trustCollectionChainPath", "/path/to/ca-cert.pem");

        // 是否信任所有证书（生产环境建议设为 false）
        properties.setProperty("nacos.remote.client.rpc.tls.trustAll", "false");

        // 构建客户端
        ConfigService configService = NacosFactory.createConfigService(properties);
        NamingService namingService = NacosFactory.createNamingService(properties);

    }

}
