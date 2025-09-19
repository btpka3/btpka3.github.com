package me.test.jdk.javax.security.auth.login;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.ConfigurationSpi;
import javax.security.auth.login.LoginContext;
import javax.security.auth.spi.LoginModule;
import java.security.Provider;
import java.security.Security;
import java.util.Arrays;
import java.util.Map;

/**
 *
 * @author dangqian.zll
 * @date 2025/9/18
 * @see sun.security.provider.ConfigFile
 * @see ConfigurationSpi
 * @see LoginModule
 * @see LoginContext
 * @see javax.security.auth.Subject
 * @see CallbackHandler
 * @see javax.security.auth.login.AppConfigurationEntry
 */
public class ConfigurationTest {


    /*
cat <<EOF > /tmp/my-jaas.conf
Client {
  org.apache.zookeeper.server.auth.DigestLoginModule required
  username="li4"
  password="456789";
};
EOF

JVM 属性： -Djava.security.auth.login.config=/tmp/my-jaas.conf
     */
    @Test
    public void testLoadFromJaasFile() {
        System.setProperty("java.security.auth.login.config", "/tmp/my-jaas.conf");

        Configuration cfg = Configuration.getConfiguration();
        System.out.println(cfg);
        AppConfigurationEntry[] entries = cfg.getAppConfigurationEntry("Client");
        System.out.println(Arrays.toString(entries));
        Assertions.assertEquals(1, entries.length);
        AppConfigurationEntry entry = entries[0];

        Assertions.assertEquals("org.apache.zookeeper.server.auth.DigestLoginModule", entry.getLoginModuleName());
        Assertions.assertSame(AppConfigurationEntry.LoginModuleControlFlag.REQUIRED, entry.getControlFlag());
        Map<String, ?> options = entry.getOptions();
        Assertions.assertEquals(2, options.size());
        Assertions.assertEquals("li4", options.get("username"));
        Assertions.assertEquals("456789", options.get("password"));
    }

    /**
     * @see JassConfJsonStrHolder#jassConfJsonStr
     */
    @SneakyThrows
    @Test
    public void testCustomSpiImpl() {

        // 编程式注册 自定 provider
        Provider provider = new MyAuthProvider("G9", "0.1.0", "demo test");
        Security.addProvider(provider);
        Assertions.assertSame(provider, Security.getProvider("G9"));

        String type = "aaa";
        Configuration.Parameters params = null;
        Configuration configuration = Configuration.getInstance(type, params);

        // 编程式注册/覆盖全局 Configuration
        Configuration.setConfiguration(configuration);

        AppConfigurationEntry[] entries = configuration.getAppConfigurationEntry("Client");
        Assertions.assertEquals(1, entries.length);
        AppConfigurationEntry entry = entries[0];


        Assertions.assertEquals("org.apache.zookeeper.server.auth.DigestLoginModule", entry.getLoginModuleName());
        Assertions.assertSame(AppConfigurationEntry.LoginModuleControlFlag.REQUIRED, entry.getControlFlag());
        Map<String, ?> options = entry.getOptions();
        Assertions.assertEquals(2, options.size());
        Assertions.assertEquals("zhang3", options.get("username"));
        Assertions.assertEquals("123456", options.get("password"));
    }


}
