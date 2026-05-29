package me.test;

import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;
import org.springframework.cloud.zookeeper.serviceregistry.ServiceInstanceRegistration;

/**
 *
 * @author dangqian.zll
 * @date 2026/5/9
 */
public class ServiceRegistryTest {

    public void x() {
        ServiceRegistry serviceRegistry = null;
        ServiceInstanceRegistration registration = null;
        ServiceInstance registration2 = buildSelfInstance();
        serviceRegistry.register(registration);
    }

    public ServiceInstance buildSelfInstance() {

        String instanceId = null;
        String serviceId = null;
        String host = null;
        int port = 0;
        boolean secure = false;
        return new DefaultServiceInstance(
                instanceId, serviceId, host, port, secure
        );
    }
}
