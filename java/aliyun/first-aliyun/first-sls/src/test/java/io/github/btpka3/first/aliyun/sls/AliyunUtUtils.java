package io.github.btpka3.first.aliyun.sls;

import com.alibaba.fastjson2.JSON;
import com.aliyun.openservices.log.common.*;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

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

    @SneakyThrows
    public static SlsMsgSourceConf getSlsMsgSourceConf(String fileName) {
        String ossConfStr = IOUtils.toString(new FileInputStream(System.getProperty("user.home") + "/Documents/data/" + fileName), StandardCharsets.UTF_8);
        return JSON.parseObject(ossConfStr, SlsMsgSourceConf.class);
    }


    public static void print(LogGroupData logGroupData) {
        FastLogGroup fastLogGroup = logGroupData.GetFastLogGroup();
        System.out.println("=========LogGroupData:" + Thread.currentThread().getName());
        System.out.println("    tags:");
        for (int i = 0; i < fastLogGroup.getLogTagsCount(); ++i) {
            FastLogTag logTag = fastLogGroup.getLogTags(i);
            System.out.printf("        %s : %s\n", logTag.getKey(), logTag.getValue());
        }

        for (int i = 0; i < fastLogGroup.getLogsCount(); ++i) {
            FastLog log = fastLogGroup.getLogs(i);
            System.out.println("----Log: " + i + ", time: " + log.getTime() + ", GetContentCount: " + log.getContentsCount());
            for (int j = 0; j < log.getContentsCount(); ++j) {
                FastLogContent content = log.getContents(j);
                System.out.printf("        %s : %s%n", content.getKey(), content.getValue());
            }
        }
    }

}
