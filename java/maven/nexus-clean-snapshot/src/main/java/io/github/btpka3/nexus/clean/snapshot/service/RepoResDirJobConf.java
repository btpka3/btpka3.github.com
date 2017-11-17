package io.github.btpka3.nexus.clean.snapshot.service;

public class RepoResDirJobConf {

    private String repo;

    private String path;

    private Integer maxSnapshotCount = 5;


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


    public Integer getMaxSnapshotCount() {
        return maxSnapshotCount;
    }

    public void setMaxSnapshotCount(Integer maxSnapshotCount) {
        this.maxSnapshotCount = maxSnapshotCount;
    }
}
