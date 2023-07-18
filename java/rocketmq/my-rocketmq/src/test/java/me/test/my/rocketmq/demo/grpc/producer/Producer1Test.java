package me.test.my.rocketmq.demo.grpc.producer;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.message.Message;
import org.apache.rocketmq.client.apis.producer.Producer;
import org.apache.rocketmq.client.apis.producer.SendReceipt;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 不使用 spring 的验证。
 *
 * @date 2023/5/18
 * @see <a href="https://github.com/apache/rocketmq-clients/blob/25df0b09abf37f134ebe2078a4205475b5e5766b/java/client/src/main/java/org/apache/rocketmq/client/java/example/ProducerNormalMessageExample.java>ProducerNormalMessageExample.java</a>
 * @see <a href="https://github.com/apache/rocketmq-clients/blob/25df0b09abf37f134ebe2078a4205475b5e5766b/java/client/src/main/java/org/apache/rocketmq/client/java/example/AsyncProducerExample.java>AsyncProducerExample.java</a>
 */
@Slf4j
public class Producer1Test {

    String topic = "yourNormalTopic";
    String tag = "TagA";

    /**
     * proxy 的地址和端口. 默认是 8081 端口。
     */
    String endpoints = "127.0.0.1:8082";
    //    String accessKey = "yourAccessKey";
    //    String secretKey = "yourSecretKey";
    String accessKey = null;
    String secretKey = null;

    @Test
    @SneakyThrows
    public void syncProducer01() {
        Producer producer = getProducer();

        // Define your message body.
        byte[] body = "This is a normal message for Apache RocketMQ".getBytes(StandardCharsets.UTF_8);
        final ClientServiceProvider provider = ClientServiceProvider.loadService();
        final Message message = provider.newMessageBuilder()
                // Set topic for the current message.
                .setTopic(topic)
                // Message secondary classifier of message besides topic.
                .setTag(tag)
                // Key(s) of the message, another way to mark message besides message id.
                .setKeys("yourMessageKey-1c151062f96e")
                .setBody(body)
                .build();
        try {
            final SendReceipt sendReceipt = producer.send(message);
            log.info("Send message successfully, messageId={}", sendReceipt.getMessageId());
        } catch (Throwable t) {
            log.error("Failed to send message", t);
        }
        // Close the producer when you don't need it anymore.
        producer.close();
//        Thread.sleep(10 * 60 * 1000);
    }

    @Test
    @SneakyThrows
    public void asyncProducer01() {
        Producer producer = getProducer();

        byte[] body = "This is a normal message for Apache RocketMQ".getBytes(StandardCharsets.UTF_8);
        final ClientServiceProvider provider = ClientServiceProvider.loadService();
        final Message message = provider.newMessageBuilder()
                // Set topic for the current message.
                .setTopic(topic)
                // Message secondary classifier of message besides topic.
                .setTag(tag)
                // Key(s) of the message, another way to mark message besides message id.
                .setKeys("yourMessageKey-0e094a5f9d85")
                .setBody(body)
                .build();
        // Set individual thread pool for send callback.
        final CompletableFuture<SendReceipt> future = producer.sendAsync(message);
        ExecutorService sendCallbackExecutor = Executors.newCachedThreadPool();
        future.whenCompleteAsync((sendReceipt, throwable) -> {
            if (null != throwable) {
                log.error("Failed to send message", throwable);
                // Return early.
                return;
            }
            log.info("Send message successfully, messageId={}", sendReceipt.getMessageId());
        }, sendCallbackExecutor);
        // Block to avoid exist of background threads.
        Thread.sleep(1 * 60 * 1000);
        // Close the producer when you don't need it anymore.
        producer.close();
    }


    @SneakyThrows
    protected Producer getProducer() {
        final ClientServiceProvider provider = ClientServiceProvider.loadService();

        // Credential provider is optional for client configuration.

//        SessionCredentialsProvider sessionCredentialsProvider = new StaticSessionCredentialsProvider(accessKey, secretKey);

        ClientConfiguration clientConfiguration = ClientConfiguration.newBuilder()
                .setEndpoints(endpoints)
//            .setCredentialProvider(sessionCredentialsProvider)
                .enableSsl(false)
                .build();

        // In most case, you don't need to create too many producers, singleton pattern is recommended.
        return provider.newProducerBuilder()
                .setClientConfiguration(clientConfiguration)

                // Set the topic name(s), which is optional but recommended. It makes producer could prefetch the topic
                // route before message publishing.
                .setTopics(topic)

                // May throw {@link ClientException} if the producer is not initialized.
                .build();
    }


}
