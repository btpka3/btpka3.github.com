package io.github.btpka3.first.aliyun.sls;

import com.aliyun.openservices.log.Client;
import com.aliyun.openservices.log.common.LogContent;
import com.aliyun.openservices.log.common.LogItem;
import com.aliyun.openservices.log.common.QueriedLog;
import com.aliyun.openservices.log.response.GetLogsResponse;
import lombok.SneakyThrows;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dangqian.zll
 * @date 2023/12/22
 * @see <a href="https://help.aliyun.com/zh/sls/user-guide/logreduce">日志聚类</a>
 */
public class LogReduce2Test {


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

        String query = "* | select a.pattern, a.count,a.signature, a.origin_signatures from (select log_reduce(3) as a from log) limit 1000";

        long now = System.currentTimeMillis();
        int from = (int) (now / 1000 - 300);
        int to = (int) (now / 1000);

        // 本示例中，query参数用于设置查询语句；line参数用于控制返回日志条数，取值为3，最大值为100。
        GetLogsResponse logsResponse = client.GetLogs(project, logStore, from, to, "", query, 3, 0, true);
        AtomicInteger c = new AtomicInteger(0);
        for (QueriedLog log : logsResponse.getLogs()) {
            LogItem item = log.GetLogItem();
            List<LogContent> contents = item.GetLogContents();
            String pattern = contents.stream()
                    .filter(content -> Objects.equals("pattern", content.GetKey()))
                    .map(LogContent::getValue)
                    .findFirst()
                    .orElse(null);
            Long count = contents.stream()
                    .filter(content -> Objects.equals("count", content.GetKey()))
                    .map(LogContent::getValue)
                    .map(Long::valueOf)
                    .findFirst()
                    .orElse(0L);
            String signature = contents.stream()
                    .filter(content -> Objects.equals("signature", content.GetKey()))
                    .map(LogContent::getValue)
                    .findFirst()
                    .orElse(null);
            String originSignatures = contents.stream()
                    .filter(content -> Objects.equals("origin_signatures", content.GetKey()))
                    .map(LogContent::getValue)
                    .findFirst()
                    .orElse(null);

            System.out.println("===========================");
            System.out.println("pattern          : " + pattern);
            System.out.println("count            : " + count);
            System.out.println("signature        : " + signature);
            System.out.println("origin_signatures: " + originSignatures);
            c.addAndGet(1);
        }
        System.out.println("Done. " + c.get());
    }


    @SneakyThrows
    @Test
    public void compareLogReduce1() {
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

        String query = "* | select v.pattern, v.signature, v.count, v.count_compare, v.diff \n" +
                "from (select compare_log_reduce(3, 300) as v from log) \n" +
                "order by v.diff desc \n" +
                "limit 1000 ";

        long now = System.currentTimeMillis();
        int from = (int) (now / 1000 - 300);
        int to = (int) (now / 1000);

        // 本示例中，query参数用于设置查询语句；line参数用于控制返回日志条数，取值为3，最大值为100。
        GetLogsResponse logsResponse = client.GetLogs(project, logStore, from, to, "", query, 3, 0, true);
        AtomicInteger c = new AtomicInteger(0);
        for (QueriedLog log : logsResponse.getLogs()) {
            LogItem item = log.GetLogItem();
            List<LogContent> contents = item.GetLogContents();
            String pattern = contents.stream()
                    .filter(content -> Objects.equals("pattern", content.GetKey()))
                    .map(LogContent::getValue)
                    .findFirst()
                    .orElse(null);
            String signature = contents.stream()
                    .filter(content -> Objects.equals("signature", content.GetKey()))
                    .map(LogContent::getValue)
                    .findFirst()
                    .orElse(null);
            Long count = contents.stream()
                    .filter(content -> Objects.equals("count", content.GetKey()))
                    .map(LogContent::getValue)
                    .map(Long::valueOf)
                    .findFirst()
                    .orElse(0L);
            Long countCompare = contents.stream()
                    .filter(content -> Objects.equals("count_compare", content.GetKey()))
                    .map(LogContent::getValue)
                    .map(Long::valueOf)
                    .findFirst()
                    .orElse(0L);

            Long diff = contents.stream()
                    .filter(content -> Objects.equals("diff", content.GetKey()))
                    .map(LogContent::getValue)
                    .map(Long::valueOf)
                    .findFirst()
                    .orElse(0L);

            System.out.println("===========================");
            System.out.println("pattern          : " + pattern);
            System.out.println("signature        : " + signature);
            System.out.println("count            : " + count);
            System.out.println("countCompare     : " + countCompare);
            System.out.println("diff             : " + diff);
            c.addAndGet(1);
        }
        System.out.println("Done. " + c.get());
    }


    @SneakyThrows
    @Test
    public void queryBySignature() {
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

        String query = "__log_signature__:3319623769693740219";
        int from = (int) (DateUtils.parseDate("2024-03-18 14:00:00", "yyyy-MM-dd HH:mm:ss").getTime() / 1000);
        int to = (int) (DateUtils.parseDate("2024-03-18 14:30:00", "yyyy-MM-dd HH:mm:ss").getTime() / 1000);

        // 本示例中，query参数用于设置查询语句；line参数用于控制返回日志条数，取值为3，最大值为100。
        GetLogsResponse logsResponse = client.GetLogs(project, logStore, from, to, "", query, 3, 0, true);
        AtomicInteger c = new AtomicInteger(0);
        for (QueriedLog log : logsResponse.getLogs()) {
            LogItem item = log.GetLogItem();
            List<LogContent> contents = item.GetLogContents();
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

            System.out.println("===========================");
            System.out.println("logRecId          : " + logRecId);
            System.out.println("signature        : " + signature);
            c.addAndGet(1);
        }
        System.out.println("Done. " + c.get());
    }
    @SneakyThrows
    @Test
    public void queryBySignature2() {
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

        String query = "*|select logRecId, __log_signature__";
        int from = (int) (DateUtils.parseDate("2024-03-18 14:00:00", "yyyy-MM-dd HH:mm:ss").getTime() / 1000);
        int to = (int) (DateUtils.parseDate("2024-03-18 14:30:00", "yyyy-MM-dd HH:mm:ss").getTime() / 1000);

        // 本示例中，query参数用于设置查询语句；line参数用于控制返回日志条数，取值为3，最大值为100。
        GetLogsResponse logsResponse = client.GetLogs(project, logStore, from, to, "", query, 3, 0, true);
        AtomicInteger c = new AtomicInteger(0);
        for (QueriedLog log : logsResponse.getLogs()) {
            LogItem item = log.GetLogItem();
            List<LogContent> contents = item.GetLogContents();
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

            System.out.println("===========================");
            System.out.println("logRecId          : " + logRecId);
            System.out.println("signature        : " + signature);
            c.addAndGet(1);
        }
        System.out.println("Done. " + c.get());
    }


}
