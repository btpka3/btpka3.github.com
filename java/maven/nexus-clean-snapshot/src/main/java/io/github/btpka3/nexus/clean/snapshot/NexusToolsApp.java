package io.github.btpka3.nexus.clean.snapshot;


import io.github.btpka3.nexus.clean.snapshot.conf.*;
import io.github.btpka3.nexus.clean.snapshot.service.*;
import org.slf4j.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.*;

@SpringBootApplication
public class NexusToolsApp {

    private static Logger logger = LoggerFactory.getLogger(NexusToolsApp.class);

    public static void main(String[] args) throws Exception {

        ConfigurableApplicationContext appContext = SpringApplication.run(NexusToolsApp.class, args);
        Props props = appContext.getBean(Props.class);


        if ("stream".equals(System.getProperty("service"))) {
            CleanDiskSnapshotByStreamService service = appContext.getBean(CleanDiskSnapshotByStreamService.class);
            service.clean(
                    props,
                    appContext::close,
                    err -> appContext.close()
            );

        } else {

            CleanDiskSnapshotService service = appContext.getBean(CleanDiskSnapshotService.class);
            service.clean(
                    props,
                    appContext::close,
                    err -> appContext.close()
            );
        }
    }

}
