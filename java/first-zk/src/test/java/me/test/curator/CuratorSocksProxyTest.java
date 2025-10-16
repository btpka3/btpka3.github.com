package me.test.curator;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.CuratorZookeeperClient;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.listen.StandardListenerManager;
import org.apache.curator.framework.state.*;
import org.apache.curator.retry.BoundedExponentialBackoffRetry;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryForever;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.ClientCnxnSocketNettyExt;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.client.ZKClientConfig;
import org.junit.jupiter.api.Test;

import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @see me.test.zookeeper.UseSocksProxyTest
 * @see RetryPolicy
 * @see RetryNTimes
 * @see RetryForever
 * @see ExponentialBackoffRetry
 * @see BoundedExponentialBackoffRetry
 * @see ConnectionStateErrorPolicy
 * @see ConnectionStateListener
 * @see <a href="https://curator.apache.org/docs/errors">Apache Curator : Error Handling</a>
 */
@Slf4j
public class CuratorSocksProxyTest {

    @SneakyThrows
    @Test
    public void test() {

        MyZkJob.restart();
        Thread.sleep(24 * 60 * 60 * 1000);

    }

    public static class MyZkJob implements Runnable, Closeable {

        private static MyZkJob job;

        private AtomicBoolean started = new AtomicBoolean(false);
        private CuratorFramework curator;

        @SneakyThrows
        public static void restart() {

            if (job != null) {
                job.close();
            }
            job = new MyZkJob();
            new Thread(job).start();
        }

        @SneakyThrows
        @Override
        public void run() {
            ZKClientConfig config = new ZKClientConfig();

            config.setProperty(ZKClientConfig.ZOOKEEPER_CLIENT_CNXN_SOCKET, ClientCnxnSocketNettyExt.class.getName());
            config.setProperty("proxy.socks5", "192.168.1.2:1080");

            System.setProperty("java.security.auth.login.config", "/tmp/jaas.conf");
            config.setProperty(ZKClientConfig.ENABLE_CLIENT_SASL_KEY, "true");
            config.setProperty(ZKClientConfig.LOGIN_CONTEXT_NAME_KEY, ZKClientConfig.LOGIN_CONTEXT_NAME_KEY_DEFAULT);

            RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
            // RetryPolicy retryPolicy = new RetryNTimes(60, 1000);

            ConnectionStateListenerManagerFactory listenerManagerFactory = (CuratorFramework c) -> {
                StandardListenerManager<ConnectionStateListener> m = StandardListenerManager.standard();
                m.addListener(new MyConnectionStateListener());
                return m;
            };

            CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                    .connectString("mse-b08b8562-p.zk.mse.aliyuncs.com")
                    // 连接超时时间（毫秒）
                    .connectionTimeoutMs(3000)
                    .connectionStateListenerManagerFactory(listenerManagerFactory)
                    .connectionStateErrorPolicy(new StandardConnectionStateErrorPolicy())

                    // 会话超时时间（毫秒）
                    .sessionTimeoutMs(10000)
                    .retryPolicy(retryPolicy)

                    .zkClientConfig(config);

            try (CuratorFramework curator = builder.build()) {
                this.curator = curator;
                curator.start();
                curator.blockUntilConnected();
                started.set(true);
                // CuratorFramework => ZooKeeper
                CuratorZookeeperClient curatorZookeeperClient = curator.getZookeeperClient();
                ZooKeeper zooKeeper = curatorZookeeperClient.getZooKeeper();

                log.info("========== START");
                loopRead(curator);
            } finally {
                started.set(false);
                log.info("========== DONE");
            }
        }


        protected void loopRead(CuratorFramework curator) {
            while (true) {
                try {
                    if (!started.get()) {
                        break;
                    }
                    read(curator);
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }

        @SneakyThrows
        protected void read(CuratorFramework curator) {
            try {
                log.info("========== READ: start");
                byte[] bytes = curator.getData().forPath("/test/dangqian");
                if (bytes != null) {
                    String str = new String(bytes, StandardCharsets.UTF_8);
                    log.info("========== RE: {}", str);
                } else {
                    log.info("========== ERR: bytes==null");
                }
            } catch (Exception e) {
                log.error("========== ERR: ", e);
            }
        }

        @Override
        public void close() throws IOException {
            started.set(false);
            if (CuratorFrameworkState.STARTED == curator.getState()) {
                curator.close();
            }
        }
    }


    public static class MyConnectionStateListener implements ConnectionStateListener {

        @Override
        public void stateChanged(CuratorFramework client, ConnectionState newState) {
            log.error("========== stateChanged: connectionState={}, curatorFramework.state={}", newState, client.getState());
            /*
            会话超时：若客户端在 sessionTimeout 内未收到服务器响应，ZooKeeper 会认为会话失效。
            自动重连：Curator 会尝试重新连接到其他可用服务器。
            状态通知：通过 ConnectionStateListener 通知应用连接状态变化。
            资源恢复：应用需手动重新注册临时节点、Watchers 或分布式锁。
            */
            if (newState == ConnectionState.LOST) {
                log.error("========== stateChanged: try to restart");
                // 注意: 同一个 CuratorFramework 不能多次启动："Cannot be started more than once"
                // client.start();
                // ⭕️ 重启（如果是基于spring框架的相关依赖初始化，则需要自行自行合理设计）
                MyZkJob.restart();
            }
        }
    }
}
