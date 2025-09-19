package me.test;

import lombok.SneakyThrows;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.client.ZKClientConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.security.auth.login.Configuration;
import java.nio.charset.StandardCharsets;
import java.security.Provider;
import java.security.Security;

/**
 *
 * @author dangqian.zll
 * @date 2025/9/19
 * @see org.apache.zookeeper.client.ZooKeeperSaslClient
 * @see sun.security.provider.ConfigFile
 * @see javax.security.auth.login.Configuration
 */
public class AuthTest {

 /*
cat <<EOF > /tmp/jaas.conf
Client {
  org.apache.zookeeper.server.auth.DigestLoginModule required
  username="li4"
  password="456789";
};
EOF
     */

    /**
     * 使用外部文件加载用户名密码
     * <p>
     * 需要增加以下JVM参数：
     * -Djava.security.auth.login.config=/tmp/jaas.conf -Dzookeeper.fips-mode=false
     */
    @SneakyThrows
    @Test
    public void auth01() {
        // 下面两个系统属性设置，由于该单测case的调用顺序可以保证是先有系统属性值，然后zookeeper client 才初始化启动
        // 真正的业务程序不能这样设置：时序错误可能会造成不生效
        System.setProperty("java.security.auth.login.config", "/tmp/jaas.conf");
        System.setProperty("zookeeper.fips-mode", "false");


        Watcher watcher = new LogWatcher();
        ZKClientConfig config = new ZKClientConfig();
        config.setProperty(ZKClientConfig.ENABLE_CLIENT_SASL_KEY, "true");
        ZooKeeper zk = new ZooKeeper("mse-b08b8562-p.zk.mse.aliyuncs.com", 3000, watcher, config);

        byte[] bytes = zk.getData("/test/dangqian", false, null);
        if (bytes != null) {
            System.out.println("========== RE:" + new String(bytes, StandardCharsets.UTF_8));
        } else {
            System.out.println("========== ERR: bytes==null");
        }
    }

    /**
     * 模拟编程读取自行获取的配置。
     */
    @SneakyThrows
    @Test
    public void auth02() {

        {
            // 编程式注册 自定 provider
            Provider provider = new MyAuthProvider("G9", "0.1.0", "demo test");
            Security.addProvider(provider);
            Assertions.assertSame(provider, Security.getProvider("G9"));

            String type = "aaa";
            Configuration.Parameters params = null;
            Configuration configuration = Configuration.getInstance(type, params);

            // 编程式注册/覆盖全局 Configuration
            Configuration.setConfiguration(configuration);
        }

        Watcher watcher = new LogWatcher();
        ZKClientConfig config = new ZKClientConfig();
        config.setProperty(ZKClientConfig.ENABLE_CLIENT_SASL_KEY, "true");
        ZooKeeper zk = new ZooKeeper("mse-b08b8562-p.zk.mse.aliyuncs.com", 3000, watcher, config);

        byte[] bytes = zk.getData("/test/dangqian", false, null);
        if (bytes != null) {
            System.out.println("========== RE:" + new String(bytes, StandardCharsets.UTF_8));
        } else {
            System.out.println("========== ERR: bytes==null");
        }
    }

}
