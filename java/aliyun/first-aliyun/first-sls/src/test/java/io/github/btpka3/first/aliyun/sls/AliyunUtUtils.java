package io.github.btpka3.first.aliyun.sls;

import com.alibaba.fastjson2.JSON;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author dangqian.zll
 * @date 2023/12/22
 */
public class AliyunUtUtils {
    @SneakyThrows
    public static AkSkConf getConfig(String fileName) {
        String ossConfStr = IOUtils.toString(new FileInputStream(System.getProperty("user.home") + "/Documents/data/" + fileName), StandardCharsets.UTF_8);
        return JSON.parseObject(ossConfStr, AkSkConf.class);
    }
}
