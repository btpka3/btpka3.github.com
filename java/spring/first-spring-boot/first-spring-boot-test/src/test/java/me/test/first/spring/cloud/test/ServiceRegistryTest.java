package me.test.first.spring.cloud.test;

import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;

import java.util.Map;

/**
 *
 * @author dangqian.zll
 * @date 2026/5/9
 */
public class ServiceRegistryTest {
    public void x() {
        ServiceRegistry serviceRegistry = null;
        ServiceInstance serviceInstance = buildSelfInstance();
        Registration registration = null;
        serviceRegistry.register(registration);

    
    }

    public ServiceInstance buildSelfInstance() {

        String instanceId = null;
        String serviceId = null;
        String host = null;
        int port = 0;
        boolean secure = false;
        Map<String, String> metadata = null;
        return new DefaultServiceInstance(
                instanceId, serviceId, host, port, secure, metadata
        );
    }
}
