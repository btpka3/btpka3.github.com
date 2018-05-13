package com.github.btpka3.dubbo.demo.provider.conf;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.alibaba.dubbo.remoting.zookeeper.ZookeeperTransporter;
import com.github.btpka3.dubbo.demo.api.DemoService;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
class DubboConf {


    @Bean
    ApplicationConfig application(BuildProperties buildProperties) {
        ApplicationConfig app = new ApplicationConfig();
        app.setName(buildProperties.getName());
        return app;
    }

    @Bean
    RegistryConfig registry() {
        RegistryConfig registry = new RegistryConfig();
//        registry.setAddress("multicast://224.5.6.7:1234?unicast=false");
//        registry.setProtocol("multicast");
//        registry.setProtocol();
//        registry.setAddress("30.6.248.12:1234");


        registry.setAddress("zookeeper://127.0.0.1:2181?client=curator");
        //registry.setProtocol("zookeeper");
//        Map<String, String> parameters = new HashMap<>();
//        parameters.put("client", "curatorFramework");
//        registry.setParameters(parameters);

        return registry;
    }

    @Bean
    ProtocolConfig protocol() {
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setPort(20880);
        protocol.setThreads(8);
        return protocol;
    }


    @Autowired
    public void dubboDemoService(
            ApplicationConfig application,
            RegistryConfig registry,
            ProtocolConfig protocol,
            DemoService demoService
    ) {
        ServiceConfig<DemoService> service = new ServiceConfig<>();
        service.setApplication(application);
        service.setRegistry(registry);
        service.setProtocol(protocol);
        service.setInterface(DemoService.class);
        service.setRef(demoService);
        service.setVersion("1.0.0");

        service.export();
    }

//    @Autowired
//    void d(CuratorFramework curatorFramework){
//        System.out.println("------------"+curatorFramework);
//    }


}
