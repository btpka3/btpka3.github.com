package me.test.jdk.javax.security.auth.login;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.ConfigurationSpi;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dangqian.zll
 * @date 2025/9/19
 */
public class MyConfigurationSpi extends ConfigurationSpi {

    static final AppConfigurationEntry[] EMPTY = new AppConfigurationEntry[0];

    private Map<String, List<AppConfigurationEntry>> map;

    public MyConfigurationSpi() {

        // TODO : 需要运行态提供动态值
        this.map = MyJaasUtils.loadFromJsonByFastjson1(JassConfJsonStrHolder.jassConfJsonStr);

    }

    @Override
    protected AppConfigurationEntry[] engineGetAppConfigurationEntry(String name) {
        if (map == null) {
            return EMPTY;
        }
        List<AppConfigurationEntry> list = map.get(name);
        if (list == null) {
            return EMPTY;
        }
        return list.toArray(EMPTY);
    }
}