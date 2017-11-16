package io.github.btpka3.nexus.clean.snapshot;

import io.github.btpka3.nexus.clean.snapshot.client.api.*;
import io.github.btpka3.nexus.clean.snapshot.service.*;
import org.slf4j.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.*;


@SpringBootApplication
public class NexusCleanApp {

    private static Logger logger = LoggerFactory.getLogger(NexusCleanApp.class);


    public static void main(String[] args) throws Exception {


        ConfigurableApplicationContext appContext = SpringApplication.run(NexusCleanApp.class, args);
        CleanSnapshotService css = appContext.getBean(CleanSnapshotService.class);

        css.clean((o) -> appContext.close(), (err) -> appContext.close());
    }

    public static void main1(String[] args) throws Exception {


        ConfigurableApplicationContext appContext = SpringApplication.run(NexusCleanApp.class, args);
        Nexus2Api nexus = appContext.getBean(Nexus2Api.class);

        appContext.registerShutdownHook();

        nexus.listRepoContent("releases", "http://mvn.kingsilk.xyz/service/local/repositories/releases/content/net/kingsilk/qh-agency-wap/1.0.0/")
                .addCallback((repoContent) -> {
                    System.out.println(repoContent);
                    appContext.close();
                    System.out.println("SSSSSSSSSS");
                }, (e) -> {
                    System.err.println(e);
                    appContext.close();
                    System.out.println("EEEEEEEEEE");
                });


        nexus.listRepoContent("releases", "/")
                .addCallback((repoContent) -> {
                    System.out.println(repoContent);
                    appContext.close();
                    System.out.println("SSSSSSSSSS");
                }, (e) -> {
                    System.err.println(e);
                    appContext.close();
                    System.out.println("EEEEEEEEEE");
                });
    }
}
