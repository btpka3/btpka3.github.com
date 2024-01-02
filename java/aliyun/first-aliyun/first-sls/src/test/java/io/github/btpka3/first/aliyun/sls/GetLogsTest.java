package io.github.btpka3.first.aliyun.sls;

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
import java.util.Objects;

/**
 * @author dangqian.zll
 * @date 2024/1/2
 * @see <a href="https://help.aliyun.com/zh/sls/developer-reference/use-getlogs-to-query-logs">阿里云：SLS：使用GetLogs接口查询日志</a>
 */
public class GetLogsTest {

    @SneakyThrows
    @Test
    public void queryLog() {

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

        // 本示例中，query参数用于设置查询语句；line参数用于控制返回日志条数，取值为3，最大值为100。
        GetLogsResponse logsResponse = client.GetLogs(project, logStore, from, to, "", query, 3, 0, true);

        Map<String, Long> map = new HashMap<>(logsResponse.getLogs().size());
        for (QueriedLog log : logsResponse.getLogs()) {
            LogItem item = log.GetLogItem();
            List<LogContent> contents = item.GetLogContents();
            String eventCode = contents.stream()
                    .filter(content -> Objects.equals("eventCode", content.GetKey()))
                    .map(LogContent::getValue)
                    .findFirst()
                    .orElse(null);
            Long count = contents.stream()
                    .filter(content -> Objects.equals("count", content.GetKey()))
                    .map(LogContent::getValue)
                    .map(Long::valueOf)
                    .findFirst()
                    .orElse(0L);

            map.put(eventCode, count);
            System.out.println(eventCode + " : " + count);
        }
        System.out.println("Done.");
    }
}
