package me.test.my.rocketmq.demo.grpc.consumer;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.SessionCredentialsProvider;
import org.apache.rocketmq.client.apis.StaticSessionCredentialsProvider;
import org.apache.rocketmq.client.apis.consumer.FilterExpression;
import org.apache.rocketmq.client.apis.consumer.FilterExpressionType;
import org.apache.rocketmq.client.apis.consumer.SimpleConsumer;
import org.apache.rocketmq.client.apis.message.MessageId;
import org.apache.rocketmq.client.apis.message.MessageView;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * 不使用 spring 的验证。
 *
 * @date 2023/5/18
 * @see <a href="https://github.com/apache/rocketmq-clients/blob/25df0b09abf37f134ebe2078a4205475b5e5766b/java/client/src/main/java/org/apache/rocketmq/client/java/example/SimpleConsumerExample.java">SimpleConsumerExample.java</a>
 * @see <a href="https://github.com/apache/rocketmq-clients/blob/25df0b09abf37f134ebe2078a4205475b5e5766b/java/client/src/main/java/org/apache/rocketmq/client/java/example/AsyncSimpleConsumerExample.java">AsyncSimpleConsumerExample.java</a>
 */
@Slf4j
public class Consumer1Test {

    String topic = "yourNormalTopic";
    String tag = "TagA";
    String consumerGroup = "yourConsumerGroup";


    /**
     * proxy 的地址和端口
     */
    String endpoints = "localhost:8082";
    String accessKey = "yourAccessKey";
    String secretKey = "yourSecretKey";


    @SneakyThrows
    @Test
    public void syncConsumer01() {
        SimpleConsumer consumer = getConsumer();
        // Max message num for each long polling.
        int maxMessageNum = 16;
        // Set message invisible duration after it is received.
        Duration invisibleDuration = Duration.ofSeconds(15);
        // Receive message, multi-threading is more recommended.
        final List<MessageView> messages = consumer.receive(maxMessageNum, invisibleDuration);
        log.info("Received {} message(s)", messages.size());
        for (MessageView message : messages) {
            final MessageId messageId = message.getMessageId();
            try {
                consumer.ack(message);
                log.info("Message is acknowledged successfully, messageId={}", messageId);
            } catch (Throwable t) {
                log.error("Message is failed to be acknowledged, messageId={}", messageId, t);
            }
        }

    }


    @Test
    @SneakyThrows
    public void asyncConsumer01() {
        SimpleConsumer consumer = getConsumer();
        // Max message num for each long polling.
        int maxMessageNum = 16;
        // Set message invisible duration after it is received.
        Duration invisibleDuration = Duration.ofSeconds(15);
        // Set individual thread pool for receive callback.
        ExecutorService receiveCallbackExecutor = Executors.newCachedThreadPool();
        // Set individual thread pool for ack callback.
        ExecutorService ackCallbackExecutor = Executors.newCachedThreadPool();
        // Receive message.
        final CompletableFuture<List<MessageView>> future0 = consumer.receiveAsync(maxMessageNum,
            invisibleDuration);
        future0.whenCompleteAsync(((messages, throwable) -> {
            if (null != throwable) {
                log.error("Failed to receive message from remote", throwable);
                // Return early.
                return;
            }
            log.info("Received {} message(s)", messages.size());
            // Using messageView as key rather than message id because message id may be duplicated.
            final Map<MessageView, CompletableFuture<Void>> map =
                messages.stream().collect(Collectors.toMap(message -> message, consumer::ackAsync));
            for (Map.Entry<MessageView, CompletableFuture<Void>> entry : map.entrySet()) {
                final MessageId messageId = entry.getKey().getMessageId();
                final CompletableFuture<Void> future = entry.getValue();
                future.whenCompleteAsync((v, t) -> {
                    if (null != t) {
                        log.error("Message is failed to be acknowledged, messageId={}", messageId, t);
                        // Return early.
                        return;
                    }
                    log.info("Message is acknowledged successfully, messageId={}", messageId);
                }, ackCallbackExecutor);
            }

        }), receiveCallbackExecutor);

        // FIXME: jdk17 启动 RocketMQ Server : 运行报错：DEADLINE_EXCEEDED
        Thread.sleep(5 * 60 * 1000);
    }

    @SneakyThrows
    protected SimpleConsumer getConsumer() {
        final ClientServiceProvider provider = ClientServiceProvider.loadService();
        // Credential provider is optional for client configuration.
        SessionCredentialsProvider sessionCredentialsProvider =
            new StaticSessionCredentialsProvider(accessKey, secretKey);


        ClientConfiguration clientConfiguration = ClientConfiguration.newBuilder()
            .setEndpoints(endpoints)
            .setCredentialProvider(sessionCredentialsProvider)
            .enableSsl(false)
            .build();

        Duration awaitDuration = Duration.ofSeconds(30);
        FilterExpression filterExpression = new FilterExpression(tag, FilterExpressionType.TAG);
        // In most case, you don't need to create too many consumers, singleton pattern is recommended.
        return provider.newSimpleConsumerBuilder()
            .setClientConfiguration(clientConfiguration)
            // Set the consumer group name.
            .setConsumerGroup(consumerGroup)
            // set await duration for long-polling.
            .setAwaitDuration(awaitDuration)
            // Set the subscription for the consumer.
            .setSubscriptionExpressions(Collections.singletonMap(topic, filterExpression))
            .build();
    }


}
