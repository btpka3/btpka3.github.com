package me.test.curator.example;

import lombok.SneakyThrows;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.client.ZKClientConfig;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.auth.DigestLoginModule;
import org.junit.jupiter.api.Test;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author dangqian.zll
 * @date 2025/9/23
 */
public class GetTest {

    @Test
    @SneakyThrows
    public void getData01() {

        String zkUser = System.getProperty("zk.user");
        String zkPwd = System.getProperty("zk.password");
//        Assertions.assertTrue(zkUser != null && zkPwd != null,
//                "please specify zookeeper username by JVM system property " +
//                        "-Dzk.user=xxx -Dzk.password=yyy"
//        );
        Configuration.setConfiguration(new SimpleSASLConfig(zkUser, zkPwd));

        String host = "mse-b08b8562-p.zk.mse.aliyuncs.com";
        ZKClientConfig zkClientConfig = new ZKClientConfig();
        CuratorFramework curator = CuratorFrameworkFactory.builder()
                .connectString(host)
                .retryPolicy(new RetryNTimes(3, 1000))
                .connectionTimeoutMs(1000)
                .sessionTimeoutMs(10000)
                .zkClientConfig(zkClientConfig)
                //.authorization()
                // etc. etc.
                .build();
        curator.start();
        // 即便未认证，但也认为 connected==true
        boolean connected = curator.blockUntilConnected(1000, java.util.concurrent.TimeUnit.MILLISECONDS);
        if (!connected) {
            System.err.println("not connected");
            return;
        }
        System.out.println("state = " + curator.getState());
        {
            byte[] bytes = curator.getData().forPath("/test/dangqian");
            String str = new String(bytes, StandardCharsets.UTF_8);
            System.out.println("result = " + str);
        }
        {
            // 存在的节点返回值非null
            Stat o = curator.checkExists().forPath("/test/dangqian");
            System.out.println("checkExists() = " + o);
        }
        {
            // 不存在的节点为null;
            Stat o = curator.checkExists().forPath("/404");
            System.out.println("checkExists() = " + o);
        }
        {

        }
        curator.close();
    }

    public class SimpleSASLConfig extends Configuration {

        AppConfigurationEntry entry;

        public SimpleSASLConfig(String username,
                                String password) {
            Map<String, String> options = new HashMap<>();
            options.put("username", username);
            options.put("password", password);
            this.entry = new AppConfigurationEntry(
                    DigestLoginModule.class.getName(),
                    AppConfigurationEntry.LoginModuleControlFlag.REQUIRED,
                    options
            );
        }

        @Override
        public AppConfigurationEntry[] getAppConfigurationEntry(String name) {
            AppConfigurationEntry[] array = new AppConfigurationEntry[1];
            array[0] = entry;
            return array;
        }
    }
}
