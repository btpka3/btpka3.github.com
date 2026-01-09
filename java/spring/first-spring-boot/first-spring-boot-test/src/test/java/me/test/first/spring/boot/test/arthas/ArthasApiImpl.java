package me.test.first.spring.boot.test.arthas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;

/**
 *
 * @author dangqian.zll
 * @date 2025/12/23
 */
public class ArthasApiImpl implements ArthasApi {

    private String url = "http://localhost:8563/api";
    private RestTemplate restTemplate = new RestTemplate();

    public ArthasApiImpl() {
    }

    public ArthasApiImpl(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public ExecResp exec(String command) {
        ExecReq req = new ExecReq();
        req.setCommand(command);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ExecReq> reqHttpEntity = new HttpEntity<>(req, headers);
        ResponseEntity<ExecResp> responseEntity = restTemplate.exchange(url, HttpMethod.POST, reqHttpEntity, ExecResp.class);
        return responseEntity.getBody();
    }

    @Override
    public InitSessionResp initSession() {
        InitSessionReq req = new InitSessionReq();
        return restTemplate.postForObject(url, req, InitSessionResp.class);
    }

    @Override
    public JoinSessionResp joinSession(String sessionId) {
        return null;
    }

    @Override
    public PullResultsResp pullResults(String sessionId, String consumerId) {
        return null;
    }

    @Override
    public AsyncExecResp asyncExec(String sessionId, String command) {
        return null;
    }

    @Override
    public InterruptJobResp interruptJob(String sessionId) {
        return null;
    }

    @Override
    public CloseSessionResp closeSession(String sessionId) {
        return null;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(toBuilder = true)
    public static class ExecReq implements Serializable {
        private static final long serialVersionUID = 1L;
        private String action = "exec";
        private String command;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(toBuilder = true)
    public static class InitSessionReq {
        private String action = "init_session";
    }
}
