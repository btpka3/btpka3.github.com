package me.test.zookeeper;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

import javax.security.auth.login.AppConfigurationEntry;
import java.io.IOException;
import java.lang.reflect.Type;

/**
 *
 * @author dangqian.zll
 * @date 2025/9/19
 */
public class LoginModuleControlFlagFastJson1Codec implements ObjectSerializer, ObjectDeserializer {

    public void write(
            JSONSerializer serializer,
            Object object,
            Object fieldName,
            Type fieldType,
            int features
    ) throws IOException {
        AppConfigurationEntry.LoginModuleControlFlag flat = (AppConfigurationEntry.LoginModuleControlFlag) object;
        String str = null;
        if (flat == AppConfigurationEntry.LoginModuleControlFlag.REQUIRED) {
            str = "required";
        } else if (flat == AppConfigurationEntry.LoginModuleControlFlag.REQUISITE) {
            str = "requisite";
        } else if (flat == AppConfigurationEntry.LoginModuleControlFlag.SUFFICIENT) {
            str = "sufficient";
        } else if (flat == AppConfigurationEntry.LoginModuleControlFlag.OPTIONAL) {
            str = "optional";
        } else {
            throw new RuntimeException("Configuration.Error.Invalid.control.flag.flag : " + str);
        }
        serializer.write(str);
    }

    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        String str = parser.getRawReader().readString();
        if (str == null) {
            return null;
        }
        AppConfigurationEntry.LoginModuleControlFlag controlFlag = null;
        switch (str) {
            case "required":
                controlFlag = AppConfigurationEntry.LoginModuleControlFlag.REQUIRED;
                break;
            case "requisite":
                controlFlag = AppConfigurationEntry.LoginModuleControlFlag.REQUISITE;
                break;
            case "sufficient":
                controlFlag = AppConfigurationEntry.LoginModuleControlFlag.SUFFICIENT;
                break;
            case "optional":
                controlFlag = AppConfigurationEntry.LoginModuleControlFlag.OPTIONAL;
                break;
            default:
                throw new RuntimeException("Configuration.Error.Invalid.control.flag.flag : " + str);
        }
        return (T) controlFlag;
    }

    @Override
    public long getFeatures() {
        return 0L;
    }
}