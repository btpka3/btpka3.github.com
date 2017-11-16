package io.github.btpka3.nexus.clean.snapshot.client.api;

import org.springframework.http.*;
import org.springframework.util.concurrent.*;

public interface Nexus2Api {

    ListenableFuture<ResponseEntity<RepoContent>> listRepoContent(String repoId, String relativePath);

    ListenableFuture<ResponseEntity<Void>> delRepoContent(String resourceURI);

}
