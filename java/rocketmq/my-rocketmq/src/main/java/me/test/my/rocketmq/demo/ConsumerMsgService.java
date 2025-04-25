package me.test.my.rocketmq.demo;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.acl.common.AclClientRPCHook;
import org.apache.rocketmq.acl.common.SessionCredentials;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.RPCHook;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

/**
 * @author dangqian.zll
 * @date 2025/4/24
 * @see org.apache.rocketmq.acl.common.SessionCredentials
 */
@Slf4j
@Component("consumerMsgService")
public class ConsumerMsgService {

    private Props props;


    public ConsumerMsgService(Props props) {
        this.props = props;
    }

    @PostConstruct
    public void init() {
        consume();
    }

    @SneakyThrows
    public void consume() {

        Props.ConsumerConf consumerConf = props.getConsumer();

        if (StringUtils.isBlank(consumerConf.getTopic())
                || StringUtils.isBlank(consumerConf.getTag())) {
            log.error("topic or tag is blank");
            return;
        }

        String consumerGroup = consumerConf.getConsumerGroup();
        String namespace = consumerConf.getNamespace();
        RPCHook rpcHook = null;
        SessionCredentials sessionCredentials = new SessionCredentials();
        String accessKey = sessionCredentials.getAccessKey();
        if (StringUtils.isNotBlank(accessKey)) {
            log.info("use accessKey:{}, topic={}, tag={}, consumerGroup={}, namespace={}", accessKey,
                    consumerConf.getTopic(), consumerConf.getTag(), consumerGroup, namespace);
            rpcHook = new AclClientRPCHook(sessionCredentials);
        }
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(namespace, consumerGroup, rpcHook);

        consumer.subscribe(consumerConf.getTopic(), consumerConf.getTag());
        consumer.registerMessageListener((List<MessageExt> msgs, ConsumeConcurrentlyContext context) -> {
            for (MessageExt msg : msgs) {
                log.info("MSG_RECEIVED, topic={}, tag={}, key={}, id={}, body={}",
                        msg.getTopic(),
                        msg.getTags(),
                        msg.getKeys(),
                        msg.getMsgId(),
                        consumerConf.isBodyToBase64()
                                ? Base64.getEncoder().encodeToString(msg.getBody())
                                : new String(msg.getBody(), StandardCharsets.UTF_8)
                );
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        // 启动Consumer
        consumer.start();
        log.info("consumerMsgService Started.");

    }


}
