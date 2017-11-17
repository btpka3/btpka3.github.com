package io.github.btpka3.nexus.clean.snapshot.client.impl;

import io.github.btpka3.nexus.clean.snapshot.client.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.util.concurrent.*;
import org.springframework.web.client.*;

import java.util.*;

@Component
public class Nexus2Impl implements Nexus2Api {

    @Value("${nexus.apiBaseUrl}")
    private String apiBaseUrl;

    @Autowired
    private AsyncRestTemplate asyncRestTemplate;

    @Override
    public ListenableFuture<ResponseEntity<RepoContent>> listRepoContent(String repoId, String relativePath) {


        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<Void> reqEntity = new HttpEntity<>(headers);

        return asyncRestTemplate.exchange(
                apiBaseUrl + "/repositories/" + repoId + "/content" + relativePath,
                HttpMethod.GET,
                reqEntity,
                RepoContent.class
        );
    }

    @Override
    public ListenableFuture<ResponseEntity<Void>> delRepoContent(String resourceUri) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<Void> reqEntity = new HttpEntity<>(headers);

        return asyncRestTemplate.exchange(
                //apiBaseUrl + "/repositories/" + repoId + "/content" + relativePath,
                resourceUri,
                HttpMethod.DELETE,
                reqEntity,
                Void.class
        );
    }

    public String getApiBaseUrl() {
        return apiBaseUrl;
    }

    public void setApiBaseUrl(String apiBaseUrl) {
        this.apiBaseUrl = apiBaseUrl;
    }
}
