package me.test.zookeeper;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.ClientCnxnSocketNettyExt;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.client.ZKClientConfig;
import org.junit.jupiter.api.Test;

import java.io.Closeable;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.apache.zookeeper.Watcher.Event.KeeperState.Expired;

/**
 *
 * @author dangqian.zll
 * @date 2025/10/15
 * @see org.apache.zookeeper.ClientCnxnSocket
 * @see org.apache.zookeeper.ClientCnxnSocketNIO
 * @see org.apache.zookeeper.ClientCnxnSocketNetty
 * @see org.apache.zookeeper.retry.ZooKeeperRetry
 * @see me.test.curator.CuratorSocksProxyTest
 */
@Slf4j
public class ZookeeperSocksProxyTest {

    /**
     * 请参考 {@link ClientCnxnSocketNettyExt} 对其进行了配置了代理。
     */
    @Test
    @SneakyThrows
    public void test() {
        MyZkJob.restart();
        Thread.sleep(24 * 60 * 60 * 1000);
    }

    public static class MyZkJob implements Runnable, Closeable {
        private static MyZkJob job;

        private AtomicBoolean started = new AtomicBoolean(false);
        private ZooKeeper zk;

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
            Watcher watcher = new MyWatcher();
            ZKClientConfig config = new ZKClientConfig();

            config.setProperty(ZKClientConfig.ZOOKEEPER_CLIENT_CNXN_SOCKET, ClientCnxnSocketNettyExt.class.getName());
            config.setProperty("proxy.socks5", "192.168.1.2:1080");

            System.setProperty("java.security.auth.login.config", "/tmp/jaas.conf");
            config.setProperty(ZKClientConfig.ENABLE_CLIENT_SASL_KEY, "true");
            config.setProperty(ZKClientConfig.LOGIN_CONTEXT_NAME_KEY, ZKClientConfig.LOGIN_CONTEXT_NAME_KEY_DEFAULT);
            // 默认值: false
            // config.setProperty(ZKClientConfig.DISABLE_AUTO_WATCH_RESET, "false");

            this.zk = new ZooKeeper("mse-b08b8562-p.zk.mse.aliyuncs.com", 3000, watcher, config);
            started.set(true);

            // 循环读取，中间可以重启代理服务器，并验证是否能自动恢复（重新读取到相关值
            loopRead(zk);
        }

        protected void loopRead(ZooKeeper zk) {
            while (true) {
                try {
                    if (!started.get()) {
                        break;
                    }
                    read(zk);
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }

        @SneakyThrows
        protected void read(ZooKeeper zk) {
            try {
                byte[] bytes = zk.getData("/test/dangqian", false, null);
                if (bytes != null) {
                    log.info("========== RE:" + new String(bytes, StandardCharsets.UTF_8));
                } else {
                    log.info("========== ERR: bytes==null");
                }
            } catch (Exception e) {
                log.error("========== ERR: ", e);
            }
        }

        @SneakyThrows
        @Override
        public void close() {
            started.set(false);
            zk.close();
        }

    }


    @Slf4j
    public static class MyWatcher implements Watcher {
        @Override
        public void process(WatchedEvent watchedEvent) {
            log.info("========== ME:{}", watchedEvent);
            if (Expired == watchedEvent.getState()) {
                log.info("========== ME: session expired, try to restart");
                MyZkJob.restart();
            }

        }
    }
}


