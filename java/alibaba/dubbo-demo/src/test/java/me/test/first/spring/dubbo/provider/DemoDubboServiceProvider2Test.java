package me.test.first.spring.dubbo.provider;

import lombok.SneakyThrows;
import me.test.first.spring.dubbo.DemoDubboService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2023/5/12
 * @see <a href="https://github.com/apache/dubbo-samples/blob/master/1-basic/dubbo-samples-api/src/main/java/org/apache/dubbo/samples/provider/Application.java">dubbo-samples/1-basic/dubbo-samples-api : provider </a>
 */
public class DemoDubboServiceProvider2Test {


    @SneakyThrows
    @Test
    public void test() {
        startDubbo();

        int minute = 10;
        System.out.println("Dubbo Service is up, Please run DemoDubboServiceConsumerTest in " + minute + " minute");
        Thread.sleep(minute * 60 * 1000);
    }

    protected void startDubbo() {

        ApplicationConfig application = new ApplicationConfig();
        application.setName("demo-provider");

        // connect registry configuration
        RegistryConfig registry = new RegistryConfig();
        //registry.setAddress("zookeeper://127.0.0.1:2181");
        registry.setAddress("nacos://nacos.default.svc.cluster.local:8848");

        // service provider protocol configuration
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("dubbo");
        protocol.setPort(-1);
        //protocol.setThreads(200);

        ServiceConfig serviceConfig = new ServiceConfig();

        serviceConfig.setApplication(application);
        serviceConfig.setRegistry(registry);
        serviceConfig.setProtocol(protocol);

        DemoDubboService2Impl demoService = new DemoDubboService2Impl();
        serviceConfig.setRef(demoService);
        serviceConfig.setInterface(DemoDubboService.class);
        serviceConfig.setGroup("group001");
        serviceConfig.setVersion("version001");
        serviceConfig.setTimeout(5000);

        // 注意：为了更好支持 Dubbo3 应用级服务发现，推荐使用新的 DubboBootstrap API。
        // https://cn.dubbo.apache.org/zh-cn/overview/mannual/java-sdk/reference-manual/config/api/
        // 故这里使用 DubboBootstrap#start(), 而不是 serviceConfig.export()
        DubboBootstrap.getInstance()
            .application(application)
            .registry(registry)
//            .service(serviceConfig)
            .start();

        DubboBootstrap.getInstance().service(serviceConfig);

        serviceConfig.export();

        //serviceConfig.export();
    }
}
