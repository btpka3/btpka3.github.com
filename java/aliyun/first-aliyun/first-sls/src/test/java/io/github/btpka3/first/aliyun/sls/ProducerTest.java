package io.github.btpka3.first.aliyun.sls;

import com.aliyun.openservices.aliyun.log.producer.*;
import com.aliyun.openservices.aliyun.log.producer.errors.ProducerException;
import com.aliyun.openservices.log.common.LogItem;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author dangqian.zll
 * @date 2024/5/13
 * @see <a href="https://help.aliyun.com/zh/sls/developer-reference/use-aliyun-log-java-producer-to-write-log-data-to-log-service">使用Aliyun Log Java Producer写入日志数据</a>
 */
public class ProducerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerTest.class);

    private static final ExecutorService threadPool = Executors.newFixedThreadPool(10);



    @Test
    public void writeSls() throws InterruptedException {

        AkSkConf akSkConf = AliyunUtUtils.getConfig("AkSkConf.more_engine.slsRead.json");
        String ak = akSkConf.getAccessKeyId();
        String sk = akSkConf.getAccessKeySecret();


        final String project = "mtee3log";
        final String logstore = "dangqian_zll_test";
        String endpoint = "cn-zhangjiakou-2-share.log.aliyuncs.com";

        ProducerConfig producerConfig = new ProducerConfig();
        final Producer producer = new LogProducer(producerConfig);
        producer.putProjectConfig(new ProjectConfig(project, endpoint, ak, sk));

        int nTask = 3;
        final AtomicLong completed = new AtomicLong(0);
        final CountDownLatch latch = new CountDownLatch(nTask);

        for (int i = 0; i < nTask; ++i) {
            Map<String, String> msgMap = new HashMap<>(8);
            msgMap.put("a", "a" + i);
            msgMap.put("b", "b" + i);

            threadPool.submit(() -> {
                //The maximum size of a LogItem (key) is 128 bytes.  The maximum size of a LogItem (value) is 1 MB.
                LogItem logItem = new LogItem();
                msgMap.forEach((k, v) -> logItem.PushBack(k, v));
                logItem.PushBack("__tag__:__hostname__", "hostName001");
                logItem.PushBack("__tag__:__path__", "/path/aaa/bbb/ccc.log");

                try {
                    String topic = "your-topic";
                    String source = "ip001";
                    producer.send(
                            project,
                            logstore,
                            topic,
                            source,
                            logItem,
                            new SampleCallback(project, logstore, logItem, completed)
                    );
                } catch (InterruptedException e) {
                    LOGGER.warn("The current thread has been interrupted during send logs.");
                } catch (Exception e) {
                    LOGGER.error("Failed to send log, logItem={}, e=", logItem, e);
                } finally {
                    latch.countDown();
                }
            });
        }

        // 只有进程退出的时候，才需要考虑如下的逻辑。
        latch.await();
        threadPool.shutdown();
        try {
            producer.close();
        } catch (InterruptedException e) {
            LOGGER.warn("The current thread has been interrupted from close.");
        } catch (ProducerException e) {
            LOGGER.info("Failed to close producer, e=", e);
        }

        LOGGER.info("All log complete, completed={}", completed.get());
    }

    private static final class SampleCallback implements Callback {
        private static final Logger LOGGER = LoggerFactory.getLogger(SampleCallback.class);
        private final String project;
        private final String logStore;
        private final LogItem logItem;
        private final AtomicLong completed;

        SampleCallback(String project, String logStore, LogItem logItem, AtomicLong completed) {
            this.project = project;
            this.logStore = logStore;
            this.logItem = logItem;
            this.completed = completed;
        }

        @Override
        public void onCompletion(Result result) {
            try {
                if (result.isSuccessful()) {
                    LOGGER.info("Send log successfully.");
                } else {
                    LOGGER.error(
                            "Failed to send log, project={}, logStore={}, logItem={}, result={}",
                            project,
                            logStore,
                            logItem.ToJsonString(),
                            result);
                }
            } finally {
                completed.getAndIncrement();
            }
        }
    }
}