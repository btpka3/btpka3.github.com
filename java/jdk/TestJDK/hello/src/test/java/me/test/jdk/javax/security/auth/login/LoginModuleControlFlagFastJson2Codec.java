package me.test.jdk.javax.security.auth.login;

import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.reader.ObjectReader;
import com.alibaba.fastjson2.writer.ObjectWriter;

import javax.security.auth.login.AppConfigurationEntry;
import java.lang.reflect.Type;

/**
 *
 * @author dangqian.zll
 * @date 2025/9/19
 */
public class LoginModuleControlFlagFastJson2Codec {


    public static class Writer implements ObjectWriter<AppConfigurationEntry.LoginModuleControlFlag> {

        @Override
        public void write(JSONWriter jsonWriter, Object object, Object fieldName, Type fieldType, long features) {
            if (object == null) {
                jsonWriter.writeNull();
                return;
            }
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
            jsonWriter.writeString(str);
        }


    }

    public static class Reader implements ObjectReader<AppConfigurationEntry.LoginModuleControlFlag> {
        @Override
        public AppConfigurationEntry.LoginModuleControlFlag readObject(JSONReader jsonReader, Type fieldType, Object fieldName, long features) {
            String str = jsonReader.readString();
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
            return controlFlag;
        }
    }
}
