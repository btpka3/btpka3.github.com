package io.github.btpka3.nexus.clean.snapshot.service;

import io.github.btpka3.nexus.clean.snapshot.client.api.*;

public class RepoContentEx {

    private RepoResDirJobConf conf;

    private RepoContent content;

    public RepoResDirJobConf getConf() {
        return conf;
    }

    public void setConf(RepoResDirJobConf conf) {
        this.conf = conf;
    }

    public RepoContent getContent() {
        return content;
    }

    public void setContent(RepoContent content) {
        this.content = content;
    }
}
