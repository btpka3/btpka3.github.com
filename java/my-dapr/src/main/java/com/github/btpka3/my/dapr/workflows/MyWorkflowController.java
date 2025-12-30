package com.github.btpka3.my.dapr.workflows;

import io.dapr.workflows.client.DaprWorkflowClient;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 *
 * @author dangqian.zll
 * @date 2025/12/4
 */
@RestController
public class MyWorkflowController {

    DaprWorkflowClient client;

    public MyWorkflowController(DaprWorkflowClient daprWorkflowClient) {
        this.client = daprWorkflowClient;
    }

    /**
     * 开始工作
     * @param body
     * @param headers
     * @return
     */
    @PostMapping(path = "/my/workflow/external/start")
    public Mono<String> externalStart(
            @RequestBody(required = false) byte[] body,
            @RequestHeader Map<String, String> headers
    ) {
        return Mono.fromSupplier(() -> {
            try {

                String instanceId = client.scheduleNewWorkflow(DemoExternalEventWorkflow.class);
                return "Started a new external-event workflow with instance ID: " + instanceId;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @PostMapping(path = "/my/workflow/external/approve")
    public Mono<String> externalStart(
            @RequestParam String instanceId,
            @RequestParam boolean approve
    ) {
        return Mono.fromSupplier(() -> {
            try {
                client.raiseEvent(instanceId, "Approval", approve);
                return "Started a new external-event workflow with instance ID: " + instanceId + ", approve=" + approve;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
