package io.github.btpka3.first.aliyun.sls;


import com.aliyun.openservices.log.Client;
import com.aliyun.openservices.log.common.*;
import com.aliyun.openservices.log.exception.LogException;
import com.aliyun.openservices.log.request.PullLogsRequest;
import com.aliyun.openservices.log.response.ListShardResponse;
import com.aliyun.openservices.log.response.PullLogsResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dangqian.zll
 * @date 2024/3/18
 * @see <a href="https://help.aliyun.com/zh/sls/user-guide/consume-log-data">日志服务/操作指南/消费与投递/实时消费/多语言应用/普通消费</a>
 */
public class Consumer1Test {

    @SneakyThrows
    @Test
    public void testPlainConsume() {


        AkSkConf akSkConf = AliyunUtUtils.getConfig("AkSkConf.more_engine.slsRead.json");
        String ak = akSkConf.getAccessKeyId();
        String sk = akSkConf.getAccessKeySecret();

        String project = "mtee3log";
        String endpoint = "cn-zhangjiakou-2-share.log.aliyuncs.com";
        String logStore = "mtee3-exception-analyse";

        // 创建日志服务 Client
        Client client = new Client(endpoint, ak, sk);
        // 查询 LogStore 的 Shard
        ListShardResponse resp = client.ListShard(project, logStore);
        System.out.printf("logstore_shard_count: %s has %d shards\n", logStore, resp.GetShards().size());

        Map<Integer, String> cursorMap = new HashMap<>();
        for (Shard shard : resp.GetShards()) {
            int shardId = shard.getShardId();
            // 从头开始消费，获取游标。（如果是从尾部开始消费，使用 Consts.CursorMode.END）
            cursorMap.put(shardId, client.GetCursor(project, logStore, shardId, Consts.CursorMode.END).GetCursor());
        }


        try {
            //while (true) {
            // 从每个Shard中获取日志
            for (Shard shard : resp.GetShards()) {
                int shardId = shard.getShardId();
                PullLogsRequest request = new PullLogsRequest(project, logStore, shardId, 1, cursorMap.get(shardId));
                PullLogsResponse response = client.pullLogs(request);
                // 日志都在日志组（LogGroup）中，按照逻辑拆分即可。
                List<LogGroupData> logGroups = response.getLogGroups();
                for (LogGroupData logGroup : logGroups) {
                    AliyunUtUtils.print(logGroup);
                }
                // 完成处理拉取的日志后，移动游标。
                cursorMap.put(shardId, response.getNextCursor());

                // 测试使用
                break;
            }
            // 这里仅仅是验证，故读取一次记录就break。
            //break;
            //}
        } catch (LogException e) {
            System.out.println("error code :" + e.GetErrorCode());
            System.out.println("error message :" + e.GetErrorMessage());
            throw e;
        }
    }


}
