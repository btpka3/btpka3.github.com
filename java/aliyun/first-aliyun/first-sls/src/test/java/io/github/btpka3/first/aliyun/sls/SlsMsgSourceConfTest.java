package io.github.btpka3.first.aliyun.sls;

import com.alibaba.fastjson.JSON;
import com.aliyun.openservices.log.Client;
import com.aliyun.openservices.log.common.LogContent;
import com.aliyun.openservices.log.common.LogItem;
import com.aliyun.openservices.log.common.QueriedLog;
import com.aliyun.openservices.log.response.GetLogsResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dangqian.zll
 * @date 2024/5/17
 */
public class SlsMsgSourceConfTest {

    @SneakyThrows
    @Test
    public void queryLog() {

        String configFile = "SlsMsgSourceConf.4c136f07-c778-46e7-bbe2-fe73be41a593.json";
        //String configFile = "SlsMsgSourceConf.d3d423a3-f0ea-48fd-9f4c-ab58c757539d.json";

        SlsMsgSourceConf slsMsgSourceConf = AliyunUtUtils.getSlsMsgSourceConf(configFile);
        String ak = slsMsgSourceConf.getAccessId();
        String sk = slsMsgSourceConf.getAccessKey();

        // 输入Project名称。
        String project = slsMsgSourceConf.getProject();
        // 设置日志服务的服务接入点。此处以杭州为例，其它地域请根据实际情况填写。
        String host = slsMsgSourceConf.getEndpoint();
        // 输入Logstore名称。
        String logStore = slsMsgSourceConf.getLogstore();

        // 创建日志服务Client。
        Client client = new Client(host, ak, sk);

        String query = "*";

        long now = System.currentTimeMillis();
        int from = (int) (now / 1000 - (60 * 60 * 24));
        int to = (int) (now / 1000);

        // 本示例中，query参数用于设置查询语句；line参数用于控制返回日志条数，取值为3，最大值为100。
        GetLogsResponse logsResponse = client.GetLogs(project, logStore, from, to, "", query, 3, 0, true);


        for (QueriedLog log : logsResponse.getLogs()) {
            Map<String, Object> map = new HashMap<>(logsResponse.getLogs().size());
            LogItem item = log.GetLogItem();
            List<LogContent> contents = item.GetLogContents();
            contents.stream().forEach(content -> map.put(content.getKey(), content.getValue()));
            System.out.println(JSON.toJSONString(map, true));
        }
        System.out.println("Done.");
    }
}
