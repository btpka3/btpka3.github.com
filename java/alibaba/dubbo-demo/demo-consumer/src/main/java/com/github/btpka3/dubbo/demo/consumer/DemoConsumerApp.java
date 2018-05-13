package com.github.btpka3.dubbo.demo.consumer;

import com.alibaba.dubbo.config.ReferenceConfig;
import com.github.btpka3.dubbo.demo.api.DemoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoConsumerApp {

    public static void main(String[] args) {

        ConfigurableApplicationContext applicationContext = SpringApplication.run(DemoConsumerApp.class, args);


        ReferenceConfig<DemoService> ref = (ReferenceConfig<DemoService>) applicationContext.getBean("doubleDemoService"); // get remote service proxy

        while (true) {
            try {
                Thread.sleep(1000);
                DemoService demoService = ref.get();
                if (demoService == null) {
                    System.out.println("未发现服务，稍后重试。");
                    continue;
                }
                String hello = demoService.sayHello("world");
                System.out.println(hello);

            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

    }

}
