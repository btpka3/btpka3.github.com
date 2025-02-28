package io.github.btpka3.first.aliyun.sls;

import com.aliyun.openservices.log.Client;
import com.aliyun.openservices.log.common.*;
import com.aliyun.openservices.log.request.BatchGetLogRequest;
import com.aliyun.openservices.log.request.PullLogsRequest;
import com.aliyun.openservices.log.response.BatchGetLogResponse;
import com.aliyun.openservices.log.response.GetCursorResponse;
import com.aliyun.openservices.log.response.GetLogsResponse;
import com.aliyun.openservices.log.response.PullLogsResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author dangqian.zll
 * @date 2024/1/2
 * @see <a href="https://help.aliyun.com/zh/sls/developer-reference/use-getlogs-to-query-logs">阿里云：SLS：使用GetLogs接口查询日志</a>
 */
public class GetLogs2Test {

    @SneakyThrows
    @Test
    public void queryLog() {

        AkSkConf akSkConf = AliyunUtUtils.getConfig("AkSkConf.cro_deliver.aone-saas-gateway-online.json");
        String ak = akSkConf.getAccessKeyId();
        String sk = akSkConf.getAccessKeySecret();

        // 输入Project名称。
        String project = "proj-xtrace-8ae6858789bbe0b2c8435628deb4e9-cn-hangzhou";
        // 设置日志服务的服务接入点。此处以杭州为例，其它地域请根据实际情况填写。
        String host = "cn-hangzhou-share.log.aliyuncs.com";
        // 输入Logstore名称。
        String logStore = "logstore-tracing";

        // 创建日志服务Client。
        Client client = new Client(host, ak, sk);

        String query = "*";

        long now = System.currentTimeMillis();
        int from = (int) (now / 1000 - 300);
        int to = (int) (now / 1000);

        // 本示例中，query参数用于设置查询语句；line参数用于控制返回日志条数，取值为3，最大值为100。
        GetLogsResponse logsResponse = client.GetLogs(project, logStore, from, to, "", query, 3, 0, true);

        Map<String, String> map = new HashMap<>(logsResponse.getLogs().size());
        for (QueriedLog log : logsResponse.getLogs()) {
            LogItem item = log.GetLogItem();
            List<LogContent> contents = item.GetLogContents();
            String spanId = contents.stream()
                    .filter(content -> Objects.equals("spanId", content.GetKey()))
                    .map(LogContent::getValue)
                    .findFirst()
                    .orElse(null);
            String kind = contents.stream()
                    .filter(content -> Objects.equals("kind", content.GetKey()))
                    .map(LogContent::getValue)
                    .findFirst()
                    .orElse(null);

            map.put(spanId, kind);
            System.out.println(spanId + " : " + kind);
        }
        System.out.println("Done.");
    }

    @SneakyThrows
    @Test
    public void querySignature() {

        AkSkConf akSkConf = AliyunUtUtils.getConfig("AkSkConf.more_engine.slsRead.json");
        String ak = akSkConf.getAccessKeyId();
        String sk = akSkConf.getAccessKeySecret();

        // 输入Project名称。
        String project = "mtee3log";
        // 设置日志服务的服务接入点。此处以杭州为例，其它地域请根据实际情况填写。
        String host = "cn-zhangjiakou-2-share.log.aliyuncs.com";
        // 输入Logstore名称。
        String logStore = "mtee3-exception-analyse";

        // 创建日志服务Client。
        Client client = new Client(host, ak, sk);

        // __log_signature__: 3319623769693740219
//        String query = "*  | select logRecId, __log_signature__ where __log_signature__= 3319623769693740219";
        String query = "*  | select logRecId, __log_signature__ where __log_signature__ != 0";
//        String query = "*  | select logRecId, __log_signature__";

        long now = System.currentTimeMillis();
        int from = (int) (now / 1000 - 300);
        int to = (int) (now / 1000);

        // 本示例中，query参数用于设置查询语句；line参数用于控制返回日志条数，取值为3，最大值为100。
        GetLogsResponse logsResponse = client.GetLogs(project, logStore, from, to, "", query, 3, 0, true);

        for (QueriedLog log : logsResponse.getLogs()) {
            LogItem item = log.GetLogItem();
            List<LogContent> contents = item.GetLogContents();
            String eventCode = contents.stream()
                    .filter(content -> Objects.equals("eventCode", content.GetKey()))
                    .map(LogContent::getValue)
                    .findFirst()
                    .orElse(null);
            String logRecId = contents.stream()
                    .filter(content -> Objects.equals("logRecId", content.GetKey()))
                    .map(LogContent::getValue)
                    .findFirst()
                    .orElse(null);
            String signature = contents.stream()
                    .filter(content -> Objects.equals("__log_signature__", content.GetKey()))
                    .map(LogContent::getValue)
                    .findFirst()
                    .orElse(null);

            System.out.println("==================");
            System.out.println("    eventCode : " + eventCode);
            System.out.println("    logRecId : " + logRecId);
            System.out.println("    signature : " + signature);
        }
        System.out.println("Done.");
    }


    @SneakyThrows
    @Test
    public void batchGetLog() {

        AkSkConf akSkConf = AliyunUtUtils.getConfig("AkSkConf.more_engine.slsRead.json");
        String ak = akSkConf.getAccessKeyId();
        String sk = akSkConf.getAccessKeySecret();

        // 输入Project名称。
        String project = "mtee3log";
        // 设置日志服务的服务接入点。此处以杭州为例，其它地域请根据实际情况填写。
        String host = "cn-zhangjiakou-2-share.log.aliyuncs.com";
        // 输入Logstore名称。
        String logStore = "mtee3-exception-analyse";

        // 创建日志服务Client。
        Client client = new Client(host, ak, sk);

        String query = "exceptionCode : 503  | select eventCode , count(*) as count group by eventCode";

        long now = System.currentTimeMillis();
        int from = (int) (now / 1000 - 300);
        int to = (int) (now / 1000);

        List<Shard> shardList = client.ListShard(project, logStore).GetShards();
        int shardId = shardList.get(0).getShardId();

        GetCursorResponse getCursorResponse = client.GetCursor(project, logStore, shardId, from);
        String cursor = getCursorResponse.GetCursor();

        int count = 3;
        BatchGetLogRequest request = new BatchGetLogRequest(project, logStore, shardId, count, cursor);
        BatchGetLogResponse batchGetLogResponse = client.BatchGetLog(request);

        List<LogGroupData> list = batchGetLogResponse.GetLogGroups();

        for (LogGroupData logGroupData : list) {
            AliyunUtUtils.print(logGroupData);
        }
        System.out.println("Done.");
    }

    @SneakyThrows
    @Test
    public void pullLogs01() {
        AkSkConf akSkConf = AliyunUtUtils.getConfig("AkSkConf.more_engine.slsRead.json");
        String ak = akSkConf.getAccessKeyId();
        String sk = akSkConf.getAccessKeySecret();

        // 输入Project名称。
        String project = "mtee3log";
        // 设置日志服务的服务接入点。此处以杭州为例，其它地域请根据实际情况填写。
        String host = "cn-zhangjiakou-2-share.log.aliyuncs.com";
        // 输入Logstore名称。
        String logStore = "mtee3-exception-analyse";

        // 创建日志服务Client。
        Client client = new Client(host, ak, sk);

        List<Shard> shardList = client.ListShard(project, logStore).GetShards();
        int shardId = shardList.get(0).getShardId();

        long now = System.currentTimeMillis();
        int from = (int) (now / 1000 - 300);
        int to = (int) (now / 1000);

        GetCursorResponse getCursorResponse = client.GetCursor(project, logStore, shardId, from);
        String cursor = getCursorResponse.GetCursor();

        PullLogsRequest pullLogsRequest = new PullLogsRequest(project, logStore, shardId, 3, cursor);
        PullLogsResponse pullLogsResponse = client.pullLogs(pullLogsRequest);

        for (LogGroupData logGroup : pullLogsResponse.getLogGroups()) {
            AliyunUtUtils.print(logGroup);
        }
        System.out.println("Done.");
    }


}
