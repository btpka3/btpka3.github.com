package io.github.btpka3.first.aliyun.sls;

import lombok.Data;

/**
 * @author dangqian.zll
 * @date 2024/5/17
 */
@Data
public class SlsMsgSourceConf {
    String accessId;
    String accessKey;
    String consumerGroup;
    String endpoint;
    String logstore;
    String project;
}
