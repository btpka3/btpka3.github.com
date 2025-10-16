package me.test.zookeeper;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.ClientCnxnSocketNettyExt;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.client.ZKClientConfig;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

/**
 *
 * @author dangqian.zll
 * @date 2025/10/15
 * @see org.apache.zookeeper.ClientCnxnSocket
 * @see org.apache.zookeeper.ClientCnxnSocketNIO
 * @see org.apache.zookeeper.ClientCnxnSocketNetty
 */
@Slf4j
public class UseSocksProxyTest {

    /**
     * 请参考 {@link ClientCnxnSocketNettyExt} 对其进行了配置了代理。
     */
    @Test
    @SneakyThrows
    public void test01() {
        Watcher watcher = new LogWatcher();
        ZKClientConfig config = new ZKClientConfig();

        config.setProperty(ZKClientConfig.ZOOKEEPER_CLIENT_CNXN_SOCKET, ClientCnxnSocketNettyExt.class.getName());
        config.setProperty("proxy.socks5", "192.168.1.2:1080");

        System.setProperty("java.security.auth.login.config", "/tmp/jaas.conf");
        config.setProperty(ZKClientConfig.ENABLE_CLIENT_SASL_KEY, "true");
        config.setProperty(ZKClientConfig.LOGIN_CONTEXT_NAME_KEY, ZKClientConfig.LOGIN_CONTEXT_NAME_KEY_DEFAULT);
        ZooKeeper zk = new ZooKeeper("mse-b08b8562-p.zk.mse.aliyuncs.com", 3000, watcher, config);

        // 循环读取，中间可以重启代理服务器，并验证是否能自动恢复（重新读取到相关值
        loopRead(zk);
    }

    protected void loopRead(ZooKeeper zk) {
        while (true) {
            try {
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
}


