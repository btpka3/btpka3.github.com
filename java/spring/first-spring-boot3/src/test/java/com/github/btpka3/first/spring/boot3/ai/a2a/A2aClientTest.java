package com.github.btpka3.first.spring.boot3.ai.a2a;

import org.a2aproject.sdk.client.*;
import org.a2aproject.sdk.client.config.ClientConfig;
import org.a2aproject.sdk.client.http.A2ACardResolver;
import org.a2aproject.sdk.client.transport.jsonrpc.JSONRPCTransport;
import org.a2aproject.sdk.client.transport.jsonrpc.JSONRPCTransportConfig;
import org.a2aproject.sdk.spec.AgentCard;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 *
 * @author dangqian.zll
 * @date 2026/4/21
 */
public class A2aClientTest {

    public void x() {
        AgentCard agentCard = new A2ACardResolver("http://localhost:1234").getAgentCard();

        // Specify configuration for the ClientBuilder
        ClientConfig clientConfig = new ClientConfig.Builder()
                .setAcceptedOutputModes(List.of("text"))
                .build();

        // Create event consumers to handle responses that will be received from the A2A server
        // (these consumers will be used for both streaming and non-streaming responses)
        List<BiConsumer<ClientEvent, AgentCard>> consumers = List.of(
                (event, card) -> {
                    if (event instanceof MessageEvent messageEvent) {
                        // handle the messageEvent.getMessage()

                    } else if (event instanceof TaskEvent taskEvent) {
                        // handle the taskEvent.getTask()

                    } else if (event instanceof TaskUpdateEvent updateEvent) {
                        // handle the updateEvent.getTask()

                    }
                }
        );

        // Create a handler that will be used for any errors that occur during streaming
        Consumer<Throwable> errorHandler = error -> {
            // handle the error.getMessage()

        };

        // Create the client using the builder
        Client client = Client
                .builder(agentCard)
                .clientConfig(clientConfig)
                .withTransport(JSONRPCTransport.class, new JSONRPCTransportConfig())
                .addConsumers(consumers)
                .streamingErrorHandler(errorHandler)
                .build();
    }
}
