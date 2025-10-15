package me.test.zookeeper;

import lombok.SneakyThrows;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.client.ZKClientConfig;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

/**
 *
 * @author dangqian.zll
 * @date 2025/10/10
 * @see org.apache.zookeeper.client.FourLetterWordMain#send4LetterWord
 */
public class ProxyTest {


    @SneakyThrows
    @Test
    public void proxy01() {
        // 下面两个系统属性设置，由于该单测case的调用顺序可以保证是先有系统属性值，然后zookeeper client 才初始化启动
        // 真正的业务程序不能这样设置：时序错误可能会造成不生效
        System.setProperty("java.security.auth.login.config", "/tmp/jaas.conf");
        //System.setProperty("zookeeper.fips-mode", "false");


        Watcher watcher = new LogWatcher();
        ZKClientConfig config = new ZKClientConfig();
        config.setProperty(ZKClientConfig.ENABLE_CLIENT_SASL_KEY, "true");
        config.setProperty(ZKClientConfig.LOGIN_CONTEXT_NAME_KEY, ZKClientConfig.LOGIN_CONTEXT_NAME_KEY_DEFAULT);
        ZooKeeper zk = new ZooKeeper("mse-b08b8562-p.zk.mse.aliyuncs.com", 3000, watcher, config);

        byte[] bytes = zk.getData("/test/dangqian", false, null);
        if (bytes != null) {
            System.out.println("========== RE:" + new String(bytes, StandardCharsets.UTF_8));
        } else {
            System.out.println("========== ERR: bytes==null");
        }
    }

}
