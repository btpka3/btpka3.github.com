package me.test.com.esotericsoftware.kryo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Map;
import lombok.SneakyThrows;
import me.test.org.apache.commons.codec.GzipBase64Utils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

public class DecodeMessage {

    @SneakyThrows
    @Test
    public void decode() {

        String fileName = "20250217161315_212BF815190A0F34D2E552723F7B0FA0_158230";

        File filePath =
                Path.of(System.getProperty("user.home"), "Downloads", fileName).toFile();
        byte[] data = IOUtils.toByteArray(new FileInputStream(filePath));

        Map ctx = MyKryoUtils.deserialize(data);
    }

    private void printJson(Map ctx) {
        System.out.println("================");
        String json = JSON.toJSONString(
                ctx,
                SerializerFeature.WriteClassName,
                SerializerFeature.WriteNonStringKeyAsString,
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue);
        System.out.println(json);
        System.out.print("================");
    }

    @Test
    public void decodeSls() throws IOException {

        String slsJsonStr = IOUtils.toString(
                DecodeMessage.class.getResourceAsStream("target/dispatch-sls.json"), StandardCharsets.UTF_8);
        Map map = JSON.parseObject(slsJsonStr);
        String body = (String) map.get("body");
        byte[] bytes = GzipBase64Utils.getBytesFromGzipBase64(body);
        // byte[] bytes = Base64.getDecoder().decode(body);
        Map ctx = MyKryoUtils.deserialize(bytes);
        printJson(ctx);
    }
}
