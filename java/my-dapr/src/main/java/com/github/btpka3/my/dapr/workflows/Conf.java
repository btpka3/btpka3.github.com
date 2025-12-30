package com.github.btpka3.my.dapr.workflows;

import io.dapr.spring.workflows.config.DaprWorkflowsConfiguration;
import io.dapr.workflows.client.DaprWorkflowClient;
import io.dapr.workflows.runtime.WorkflowRuntimeBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author dangqian.zll
 * @date 2025/12/4
 * @see DaprWorkflowsConfiguration
 */
@Configuration
public class Conf {

    @Bean
    public DaprWorkflowClient daprWorkflowClient() {
        DaprWorkflowClient client = new DaprWorkflowClient();
        return client;
    }

    @Bean
    WorkflowRuntimeBuilder workflowRuntimeBuilder() {
        return new WorkflowRuntimeBuilder();
    }
}
