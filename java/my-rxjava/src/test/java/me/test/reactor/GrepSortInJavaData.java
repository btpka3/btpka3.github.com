package me.test.reactor;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 每周一题：用JAVA代码完成以下功能，要求使用多线程
 * cat /home/admin/logs/test/*.log | grep "Login" | sort | uniq -c | sort -nr
 *
 * @date 2018-12-22
 */
public class GrepSortInJavaData {

    static final Logger log = LoggerFactory.getLogger(GrepSortInJavaData.class);


    @Test
    public void test01() throws IOException, InterruptedException {

        String[] strArr = new String[]{
                "Login",
                "Logout"
        };


        List<String> recList = new ArrayList<>();

        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            buf.setLength(0);
            buf.append(RandomUtils.nextInt(1000, 2000))
                    .append(" ")
                    .append(strArr[RandomUtils.nextInt() % strArr.length]);
            recList.add(buf.toString());
        }

        File dir = new File("/Users/zll/logs/test");
        FileUtils.deleteDirectory(dir);
        dir.mkdirs();

        for (int i = 0; i < 3; i++) {

            OutputStreamWriter writer = new OutputStreamWriter(
                    new FileOutputStream(new File(dir, "data_" + i + ".log")),
                    StandardCharsets.UTF_8
            );

            for (int j = 0; j < 50; j++) {
                writer.write(recList.get(RandomUtils.nextInt() % recList.size()));
                writer.write("\n");
            }

            writer.close();
        }
    }


}
