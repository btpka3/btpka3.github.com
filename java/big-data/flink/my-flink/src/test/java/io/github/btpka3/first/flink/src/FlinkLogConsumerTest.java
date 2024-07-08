package io.github.btpka3.first.flink.src;

import com.aliyun.openservices.log.flink.ConfigConstants;
import com.aliyun.openservices.log.flink.FlinkLogConsumer;
import com.aliyun.openservices.log.flink.data.RawLogGroupList;
import com.aliyun.openservices.log.flink.data.RawLogGroupListDeserializer;
import com.aliyun.openservices.log.flink.util.Consts;
import org.apache.flink.api.connector.source.Source;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.junit.jupiter.api.Test;

import java.util.Properties;

/**
 * @author dangqian.zll
 * @date 2024/7/3
 * @see <a href="https://github.com/aliyun/aliyun-log-flink-connector/blob/master/README_CN.md">aliyun-log-flink-connector</a>
 * @see com.aliyun.openservices.log.flink.FlinkLogConsumer
 */
public class FlinkLogConsumerTest {
    @Test
    public void readAliyunSls() {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();


        String project = null;
        String logstore = null;

        // 设置日志服务的消息反序列化方法
        RawLogGroupListDeserializer deserializer = new RawLogGroupListDeserializer();

        Properties configProps = new Properties();
        // 设置访问日志服务的域名
        configProps.put(ConfigConstants.LOG_ENDPOINT, "cn-hangzhou.log.aliyuncs.com");
        // 设置访问ak
        configProps.put(ConfigConstants.LOG_ACCESSKEYID, "");
        configProps.put(ConfigConstants.LOG_ACCESSKEY, "");
        // 设置日志服务的project
        configProps.put(ConfigConstants.LOG_PROJECT, "ali-cn-hangzhou-sls-admin");
        // 设置日志服务的LogStore
        configProps.put(ConfigConstants.LOG_LOGSTORE, "sls_consumergroup_log");
        // 设置消费日志服务起始位置
        configProps.put(ConfigConstants.LOG_CONSUMER_BEGIN_POSITION, Consts.LOG_END_CURSOR);
        // 设置每次读取的LogGroup个数,默认100
        configProps.put(ConfigConstants.LOG_MAX_NUMBER_PER_FETCH, "100");


        // FIXME: 没有对应的 Source 接口实现类
        Source src = null;
        SourceFunction<RawLogGroupList> source = new FlinkLogConsumer<>(project, logstore, deserializer, configProps);
        DataStream<RawLogGroupList> logTestStream = env.addSource(source);

    }
}
