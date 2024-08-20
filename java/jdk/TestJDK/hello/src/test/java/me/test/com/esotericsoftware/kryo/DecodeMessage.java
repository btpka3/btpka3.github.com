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

        // 黑猫发送的
        String fileName = "20240819203434_211B4237C17B11586A185DBC1D0E0002_158230";

        // mtee3_hitrisk
//        String fileName = "20240730120217_2140CA83192D68B07FE097DE9A02B4A1_158230";


        File filePath = Path.of(System.getProperty("user.home"), "Downloads", fileName).toFile();
        byte[] data = IOUtils.toByteArray(new FileInputStream(filePath));

        Map ctx = MyKryoUtils.deserialize(data);
        System.out.print("================");
        String json = JSON.toJSONString(
                ctx,
                //SerializerFeature.WriteClassName,
                SerializerFeature.WriteNonStringKeyAsString,
                SerializerFeature.PrettyFormat
        );
        System.out.println(filePath + ":\n" + json);
        System.out.print("================");

    }
}
