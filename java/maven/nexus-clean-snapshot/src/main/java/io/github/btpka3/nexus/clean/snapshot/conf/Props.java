package io.github.btpka3.nexus.clean.snapshot.conf;

import org.springframework.boot.context.properties.*;
import org.springframework.stereotype.*;

@Component
@ConfigurationProperties(prefix = "nexus")
public class Props {

    private String sonartypeWorkDir;
    private String repoId = "snapshots";
    private Integer maxSnapshotCount = 5;

    public String getSonartypeWorkDir() {
        return sonartypeWorkDir;
    }

    public void setSonartypeWorkDir(String sonartypeWorkDir) {
        this.sonartypeWorkDir = sonartypeWorkDir;
    }

    public String getRepoId() {
        return repoId;
    }

    public void setRepoId(String repoId) {
        this.repoId = repoId;
    }

    public Integer getMaxSnapshotCount() {
        return maxSnapshotCount;
    }

    public void setMaxSnapshotCount(Integer maxSnapshotCount) {
        this.maxSnapshotCount = maxSnapshotCount;
    }
}
