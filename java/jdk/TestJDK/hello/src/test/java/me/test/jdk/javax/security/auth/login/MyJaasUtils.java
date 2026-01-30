package me.test.jdk.javax.security.auth.login;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.reader.ObjectReader;
import com.alibaba.fastjson2.reader.ObjectReaderProvider;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import javax.security.auth.login.AppConfigurationEntry;

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

    public static Map<String, List<AppConfigurationEntry>> loadFromJsonByFastjson2(String jsonStr) {
        ObjectReaderProvider provider = new ObjectReaderProvider();
        provider.register(AppConfigurationEntry.LoginModuleControlFlag.class, new LoginModuleControlFlagFastJson2Codec.Reader());
        JSONReader.Context context = new JSONReader.Context(provider);
        Type type = new TypeReference<Map<String, List<AppConfigurationEntry>>>() {
        }.getType();

        try (JSONReader reader = JSONReader.of(jsonStr, context)) {
            final ObjectReader<Map<String, List<AppConfigurationEntry>>> objectReader = provider.getObjectReader(type);
            return objectReader.readObject(reader, type, null, 0);
        }
    }

}
