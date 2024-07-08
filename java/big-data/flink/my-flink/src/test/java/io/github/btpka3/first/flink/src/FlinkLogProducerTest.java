package io.github.btpka3.first.flink.src;

import com.aliyun.openservices.log.flink.ConfigConstants;
import com.aliyun.openservices.log.flink.FlinkLogProducer;
import com.aliyun.openservices.log.flink.data.RawLog;
import com.aliyun.openservices.log.flink.data.RawLogGroup;
import com.aliyun.openservices.log.flink.model.LogSerializationSchema;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * @author dangqian.zll
 * @date 2024/7/3
 * @see <a href="https://github.com/aliyun/aliyun-log-flink-connector/blob/master/README_CN.md">aliyun-log-flink-connector</a>
 */
public class FlinkLogProducerTest {
    public static String sEndpoint = "cn-hangzhou.log.aliyuncs.com";
    public static String sAccessKeyId = "";
    public static String sAccessKey = "";
    public static String sProject = "ali-cn-hangzhou-sls-admin";
    public static String sLogstore = "test-flink-producer";
    private static final Logger LOG = LoggerFactory.getLogger(FlinkLogProducerTest.class);

    @Test
    public void writeAliyunSls() throws Exception {
        String[] args = new String[]{};
        final ParameterTool params = ParameterTool.fromArgs(args);
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.getConfig().setGlobalJobParameters(params);
        env.setParallelism(3);

        DataStream<String> simpleStringStream = env.addSource(new EventsGenerator());

        Properties configProps = new Properties();
        // 设置访问日志服务的域名
        configProps.put(ConfigConstants.LOG_ENDPOINT, sEndpoint);
        // 设置访问日志服务的ak
        configProps.put(ConfigConstants.LOG_ACCESSKEYID, sAccessKeyId);
        configProps.put(ConfigConstants.LOG_ACCESSKEY, sAccessKey);
        // 设置日志写入的日志服务project
        configProps.put(ConfigConstants.LOG_PROJECT, sProject);
        // 设置日志写入的日志服务logStore
        configProps.put(ConfigConstants.LOG_LOGSTORE, sLogstore);

        FlinkLogProducer<String> logProducer = new FlinkLogProducer<>(new SimpleLogSerializer(), configProps);

        simpleStringStream.addSink(logProducer);

        env.execute("flink log producer");



    }

    // 将数据序列化成日志服务的数据格式
    public static class SimpleLogSerializer implements LogSerializationSchema<String> {

        public RawLogGroup serialize(String element) {
            RawLogGroup rlg = new RawLogGroup();
            RawLog rl = new RawLog();
            rl.setTime((int) (System.currentTimeMillis() / 1000));
            rl.addContent("message", element);
            rlg.addLog(rl);
            return rlg;
        }
    }

    // 模拟产生日志
    public static class EventsGenerator implements SourceFunction<String> {
        private boolean running = true;

        @Override
        public void run(SourceContext<String> ctx) throws Exception {
            long seq = 0;
            while (running) {
                Thread.sleep(10);
                ctx.collect((seq++) + "-" + RandomStringUtils.randomAlphabetic(12));
            }
        }

        @Override
        public void cancel() {
            running = false;
        }
    }
}
