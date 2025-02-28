package me.test.first.aliyun.oss;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author dangqian.zll
 * @date 2024/6/3
 */
@Data
public class AkSkConf {
    private String accessKeyId;
    private String accessKeySecret;

    @SneakyThrows
    public static AkSkConf getAkSkConf(String fileName) {
        String akSkConfFile = System.getProperty("user.home") + "/Documents/data/" + fileName;
        String akSkConfJson = IOUtils.toString(new FileInputStream(akSkConfFile), StandardCharsets.UTF_8);
        return JSON.parseObject(akSkConfJson, AkSkConf.class);
    }

}
