package io.github.btpka3.first.aliyun.sls;


import com.aliyun.openservices.log.common.LogGroupData;
import com.aliyun.openservices.loghub.client.ClientWorker;
import com.aliyun.openservices.loghub.client.ILogHubCheckPointTracker;
import com.aliyun.openservices.loghub.client.config.LogHubConfig;
import com.aliyun.openservices.loghub.client.exceptions.LogHubCheckPointException;
import com.aliyun.openservices.loghub.client.interfaces.ILogHubProcessor;
import com.aliyun.openservices.loghub.client.interfaces.ILogHubProcessorFactory;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author dangqian.zll
 * @date 2024/3/18
 * @see <a href="https://help.aliyun.com/zh/sls/user-guide/use-consumer-groups-to-consume-data">通过消费组消费数据</a>
 */
public class Consumer2Test {

    @SneakyThrows
    @Test
    public void testSubscribe() {


        AkSkConf akSkConf = AliyunUtUtils.getConfig("AkSkConf.more_engine.slsRead.json");
        String ak = akSkConf.getAccessKeyId();
        String sk = akSkConf.getAccessKeySecret();

        String consumerGroup = "test_group_dangqian";
        String project = "mtee3log";
        String endpoint = "cn-zhangjiakou-2-share.log.aliyuncs.com";
        String logStore = "mtee3-exception-analyse";

        // maxFetchLogGroupSize用于设置每次从服务端获取的LogGroup最大数目，使用默认值即可。
        // 您可以使用config.setMaxFetchLogGroupSize(100);调整，取值范围为(0,1000]。
        LogHubConfig config = new LogHubConfig(
                consumerGroup,
                "consumer_1",
                endpoint,
                project,
                logStore,
                ak,
                sk,
                LogHubConfig.ConsumePosition.BEGIN_CURSOR,
                1000
        );
        config.setMaxFetchLogGroupSize(10);


        ClientWorker worker = new ClientWorker(new SampleLogHubProcessorFactory(), config);
        Thread thread = new Thread(worker);
        // Thread运行之后，ClientWorker会自动运行，ClientWorker扩展了Runnable接口。
        thread.start();
        Thread.sleep(60 * 60 * 1000);
        // 调用Worker的Shutdown函数，退出消费实例，关联的线程也会自动停止。
        worker.shutdown();
        // ClientWorker运行过程中会生成多个异步的任务。Shutdown完成后，请等待还在执行的任务安全退出。建议设置sleep为30秒。
        Thread.sleep(30 * 1000);
    }


    public static class SampleLogHubProcessor implements ILogHubProcessor {
        private int shardId;
        // 记录上次持久化Checkpoint的时间。
        private long mLastSaveTime = 0;

        // initialize 方法会在 processor 对象初始化时被调用一次
        public void initialize(int shardId) {
            this.shardId = shardId;
        }

        // 消费数据的主逻辑，消费时的所有异常都需要处理，不能直接抛出。
        public String process(List<LogGroupData> logGroups, ILogHubCheckPointTracker checkPointTracker) {
            // 打印已获取的数据。
            for (LogGroupData logGroup : logGroups) {
                AliyunUtUtils.print(logGroup);
            }
            long curTime = System.currentTimeMillis();
            // 每隔30秒，写一次Checkpoint到服务端。如果30秒内发生Worker异常终止，新启动的Worker会从上一个Checkpoint获取消费数据，可能存在少量的重复数据。
            try {
                if (curTime - mLastSaveTime > 30 * 1000) {
                    // 参数为true表示立即手动将Checkpoint更新到服务端。此外，默认每60秒会自动将内存中缓存的Checkpoint更新到服务端。
                    checkPointTracker.saveCheckPoint(true);
                    mLastSaveTime = curTime;
                } else {
                    // 参数为false表示将Checkpoint缓存在本地，可被自动更新Checkpoint机制更新到服务端。
                    checkPointTracker.saveCheckPoint(false);
                }
            } catch (LogHubCheckPointException e) {
                e.printStackTrace();
            }
            return null;
        }

        // 当Worker退出时，会调用该函数，您可以在此处执行清理工作。
        public void shutdown(ILogHubCheckPointTracker checkPointTracker) {
            // 将Checkpoint立即保存到服务端。
            try {
                checkPointTracker.saveCheckPoint(true);
            } catch (LogHubCheckPointException e) {
                e.printStackTrace();
            }
        }
    }

    public static class SampleLogHubProcessorFactory implements ILogHubProcessorFactory {
        public ILogHubProcessor generatorProcessor() {
            // 生成一个消费实例。注意：每次调用 generatorProcessor 方法，都应该返回一个新的 SampleLogHubProcessor 对象。
            return new SampleLogHubProcessor();
        }
    }

}
