package me.test.first.spring.dubbo.consumer;

import lombok.SneakyThrows;
import me.test.first.spring.dubbo.DemoDubboService;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.junit.jupiter.api.Test;

/**
 * 不依赖 spring 的 纯java编程方式启动 consumer
 *
 * @author dangqian.zll
 * @date 2023/5/12
 * @see <a href="https://github.com/apache/dubbo-samples/blob/master/1-basic/dubbo-samples-api/src/main/java/org/apache/dubbo/samples/client/Application.java">dubbo-samples/1-basic/dubbo-samples-api : client </a>
 */
public class DemoDubboServiceConsumer2Test {

    //    String REGISTRY_ADDR = "zookeeper://127.0.0.1:2181";
    String REGISTRY_ADDR = "nacos://nacos.default.svc.cluster.local:8848";


    @SneakyThrows
    @Test
    public void test() {
        DemoDubboService demoDubboService = startDubbo();
        for (int i = 0; i < 3; i++) {
            String result = demoDubboService.sayHello("zhang3");
            System.out.println("result = " + result);
        }
    }

    protected DemoDubboService startDubbo() {

        ReferenceConfig<DemoDubboService> reference = new ReferenceConfig<>();
        reference.setInterface(DemoDubboService.class);
        reference.setGroup("group001");
        reference.setVersion("version001");
        reference.setCheck(false);
        reference.setTimeout(5000);

        DubboBootstrap.getInstance()
                .application("first-dubbo-consumer")
                //.registry(new RegistryConfig("zookeeper://127.0.0.1:2181"))
                .registry(new RegistryConfig(REGISTRY_ADDR))
                //.reference(reference)

                .start();

        DubboBootstrap.getInstance().reference(reference);

        return reference.get();
    }

//    @Test
//    public void tryCreateReference() {
//
//        // ReferenceConfig 必须在 DubboBootstrap#start() 之后。
//        // java.lang.IllegalStateException: Default config not found for ApplicationConfig
//        Assertions.assertThrows(IllegalStateException.class, () -> {
//            ReferenceConfig<DemoDubboService> reference = new ReferenceConfig<>();
//            reference.setInterface(DemoDubboService.class);
//            reference.setGroup("group001");
//            reference.setVersion("version001");
//            reference.setCheck(false);
//            reference.setTimeout(5000);
//            reference.get();
//        });
//
//
//    }
}
