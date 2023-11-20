package io.github.btpka3.first.spring.cloud.kubernetes;

import org.junit.jupiter.api.Test;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.kubernetes.client.discovery.KubernetesInformerDiscoveryClient;

import java.util.List;

/**
 * @author dangqian.zll
 * @date 2023/11/20
 * @see KubernetesInformerDiscoveryClient
 */
public class DiscoveryClientTest {

    private DiscoveryClient discoveryClient;

    @Test
    public void test() {
        String serviceId = "hsf://com.alibaba.security.tenant.common.service.RequestService:1.0.0_content:HSF";
        List<ServiceInstance> list = discoveryClient.getInstances(serviceId);
    }


}
