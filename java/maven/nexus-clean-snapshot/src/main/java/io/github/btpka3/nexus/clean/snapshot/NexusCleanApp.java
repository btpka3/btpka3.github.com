package io.github.btpka3.nexus.clean.snapshot;

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

        String repo = appContext.getEnvironment().getProperty("nexus.repo");
        String path = appContext.getEnvironment().getProperty("nexus.repoRootRsc", "/");
        Integer maxCount = Integer.parseInt(appContext.getEnvironment().getProperty("nexus.maxSnapshotCount", "5"));

        RepoResDirJobConf conf = new RepoResDirJobConf();
        conf.setRepo(repo);
        conf.setPath(path);
        conf.setMaxSnapshotCount(maxCount);

        css.clean(
                conf,
                appContext::close,
                err -> appContext.close()
        );
    }

}
