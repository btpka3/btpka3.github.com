package me.test.com.esotericsoftware.kryo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.util.Map;

public class DecodeMessage {


    @SneakyThrows
    @Test
    public void decode() {

        String fileName = "20241223162303_213F4C0014BE2394271E7371C25E7FB9_158230";

        File filePath = Path.of(System.getProperty("user.home"), "Downloads", fileName).toFile();
        byte[] data = IOUtils.toByteArray(new FileInputStream(filePath));

        Map ctx = MyKryoUtils.deserialize(data);
        System.out.print("================");
        String json = JSON.toJSONString(
                ctx,
                //SerializerFeature.WriteClassName,
                SerializerFeature.WriteNonStringKeyAsString,
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteMapNullValue
        );
        System.out.println(filePath + ":\n" + json);
        System.out.print("================");

    }
}
