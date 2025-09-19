package me.test.jdk.javax.security.auth.login;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.security.auth.login.AppConfigurationEntry;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dangqian.zll
 * @date 2025/9/19
 */
public class MyJaasUtilsTest {
    @SneakyThrows
    @Test
    public void loadFromJsonByFastjson1Test() {
        Map<String, List<AppConfigurationEntry>> map = MyJaasUtils.loadFromJsonByFastjson1(JassConfJsonStrHolder.jassConfJsonStr);
        Assertions.assertEquals(1, map.size());
        List<AppConfigurationEntry> list = map.get("Client");
        Assertions.assertEquals(1, list.size());
        AppConfigurationEntry entry = list.get(0);

        Assertions.assertEquals("org.apache.zookeeper.server.auth.DigestLoginModule", entry.getLoginModuleName());
        Assertions.assertSame(AppConfigurationEntry.LoginModuleControlFlag.REQUIRED, entry.getControlFlag());
        Map<String, ?> options = entry.getOptions();
        Assertions.assertEquals(2, options.size());
        Assertions.assertEquals("zhang3", options.get("username"));
        Assertions.assertEquals("123456", options.get("password"));
    }

    @SneakyThrows
    @Test
    public void loadFromJsonByFastjson2Test() {
        Map<String, List<AppConfigurationEntry>> map = MyJaasUtils.loadFromJsonByFastjson2(JassConfJsonStrHolder.jassConfJsonStr);
        Assertions.assertEquals(1, map.size());
        List<AppConfigurationEntry> list = map.get("Client");
        Assertions.assertEquals(1, list.size());
        AppConfigurationEntry entry = list.get(0);

        Assertions.assertEquals("org.apache.zookeeper.server.auth.DigestLoginModule", entry.getLoginModuleName());
        Assertions.assertSame(AppConfigurationEntry.LoginModuleControlFlag.REQUIRED, entry.getControlFlag());
        Map<String, ?> options = entry.getOptions();
        Assertions.assertEquals(2, options.size());
        Assertions.assertEquals("zhang3", options.get("username"));
        Assertions.assertEquals("123456", options.get("password"));
    }

}
