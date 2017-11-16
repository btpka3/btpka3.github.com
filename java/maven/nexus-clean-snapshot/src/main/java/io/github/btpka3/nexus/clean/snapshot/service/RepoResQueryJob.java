package io.github.btpka3.nexus.clean.snapshot.service;

import io.github.btpka3.nexus.clean.snapshot.client.api.*;
import org.slf4j.*;

import java.util.function.*;


public class RepoResQueryJob implements Consumer<RepoResDirJobConf> {

    private Logger logger = LoggerFactory.getLogger(getClass());

//    BlockingDeque<RepoContentWithParent> q = new LinkedBlockingDeque<>();
//    AtomicReference<Throwable> errRef = new AtomicReference<>();
//    AtomicBoolean completed = new AtomicBoolean(false);

//    @Autowired
//    private RepoResMonitor repoResMonitor;

//    @Autowired
//    private Nexus2Api nexus2Api;
//
//    @Autowired
//    private ApplicationContext applicationContext;
//
//    @Value("${nexus.repo}")
//    private String repo;
//
//    private String path;


    @Override
    public void accept(RepoResDirJobConf conf) {
        Nexus2Api nexus2Api = conf.getApplicationContext().getBean(Nexus2Api.class);
        String repo = conf.getRepo();
        String path = conf.getPath();

        logger.debug("==================== 开始查询 : " + path);
        nexus2Api.listRepoContent(repo, path)
                .addCallback(
                        c -> {
                            RepoContentEx p = new RepoContentEx();
                            p.setConf(conf);
                            p.setContent(c.getBody());
                            conf.getSuccessCallback().accept(p);
                        },
                        err -> {
                            conf.getErrorCallback().accept(err);
                        }
                );
    }
}
