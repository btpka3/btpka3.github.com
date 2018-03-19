package io.github.btpka3.font.crawler;


import io.github.btpka3.font.crawler.service.MyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;

@SpringBootApplication(
//        exclude = {
//                ServletWebServerFactoryAutoConfiguration.class,
//                WebMvcAutoConfiguration.class
//        }
)
public class FontCrawlerApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext appCtx = SpringApplication.run(FontCrawlerApp.class, args);

        File tmp = new File(".tmp/font");
        if (!tmp.exists()) {
            tmp.mkdirs();
        }


        MyService myService = appCtx.getBean(MyService.class);

//        if (args.length > 0) {
//            if ("json".equals(args[0])) {
//                myService.json();
//            } else if ("download".equals(args[0])) {
//                myService.download();
//            }
//        }
        myService.download();
        System.out.println(myService.add(1, 2));
    }
}
