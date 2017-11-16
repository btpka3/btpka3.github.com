package io.github.btpka3.nexus.clean.snapshot.service;

import org.springframework.context.*;

import java.util.function.*;

public class RepoResDirJobConf {

    private String repo;

    private String path;

    private ApplicationContext applicationContext;

    private Consumer<RepoContentEx> successCallback = (c) -> {
    };

    private Consumer<Throwable> errorCallback = (t) -> {
    };

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Consumer<RepoContentEx> getSuccessCallback() {
        return successCallback;
    }

    public void setSuccessCallback(Consumer<RepoContentEx> successCallback) {
        this.successCallback = successCallback;
    }

    public Consumer<Throwable> getErrorCallback() {
        return errorCallback;
    }

    public void setErrorCallback(Consumer<Throwable> errorCallback) {
        this.errorCallback = errorCallback;
    }


}
