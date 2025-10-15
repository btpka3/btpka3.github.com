package me.test.zookeeper;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.ParserConfig;

import javax.security.auth.login.AppConfigurationEntry;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dangqian.zll
 * @date 2025/9/19
 */
public class MyJaasUtils {


    public static Map<String, List<AppConfigurationEntry>> loadFromJsonByFastjson1(String jsonStr) {

        Type type = new TypeReference<Map<String, List<AppConfigurationEntry>>>() {
        }.getType();

        ParserConfig config = new ParserConfig();
        config.putDeserializer(AppConfigurationEntry.LoginModuleControlFlag.class, new LoginModuleControlFlagFastJson1Codec());

        return com.alibaba.fastjson.JSON.parseObject(jsonStr, type, config);
    }

}
