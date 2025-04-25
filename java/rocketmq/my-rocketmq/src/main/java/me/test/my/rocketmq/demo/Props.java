package me.test.my.rocketmq.demo;

import lombok.Data;
import org.apache.rocketmq.common.MixAll;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author dangqian.zll
 * @date 2025/4/24
 */
@ConfigurationProperties(prefix = Props.PREFIX)
@Data
public class Props {

    public static final String PREFIX = "my.rocketmq";
    private ConsumerConf consumer;

    @Data
    public static class ConsumerConf {
        private String topic;
        private String tag = "*";
        private String consumerGroup = MixAll.DEFAULT_CONSUMER_GROUP;
        private boolean bodyToBase64;
        private String namespace;
    }
}
