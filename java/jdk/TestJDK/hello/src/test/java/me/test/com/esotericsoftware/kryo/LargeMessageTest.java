package me.test.com.esotericsoftware.kryo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Map;

/**
 * @author dangqian.zll
 * @date 2024/4/25
 */
public class LargeMessageTest {


    protected boolean isJsonObjStr(byte[] data) {
        return data.length > 2 && data[0] == '{' && data[1] == '"';
    }

    @SneakyThrows
    @Test
    public void decode() {

        // 从SLS下载的单行日志（完整JSON）
        String fileName = "test1.json";

        File filePath = Path.of(System.getProperty("user.home"), "Downloads", fileName).toFile();
        String slsRecJsonStr = IOUtils.toString(new FileInputStream(filePath), StandardCharsets.UTF_8);
        JSONObject jsonObj = (JSONObject) JSON.parse(slsRecJsonStr);
        String base64MsgStr = (String) jsonObj.get("message");
        byte[] data = Base64.decodeBase64(base64MsgStr);


        System.out.println("================ " + filePath);
        if (isJsonObjStr(data)) {
            System.out.println(JSON.toJSONString(
                    JSON.parse(new String(data, StandardCharsets.UTF_8)),
                    SerializerFeature.PrettyFormat
            ));
        } else {
            Map ctx = MyKryoUtils.deserialize(data);
            String json = JSON.toJSONString(
                    ctx,
                    //SerializerFeature.WriteClassName,
                    SerializerFeature.WriteNonStringKeyAsString,
                    SerializerFeature.PrettyFormat
            );
            System.out.println(json);
        }


        System.out.print("================");

    }
}
