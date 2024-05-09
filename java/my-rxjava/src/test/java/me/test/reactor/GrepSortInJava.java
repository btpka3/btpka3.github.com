package me.test.reactor;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.concurrent.CountDownLatch;
import java.util.function.Function;
import java.util.stream.BaseStream;

/**
 * 每周一题：用JAVA代码完成以下功能，要求使用多线程
 * cat /home/admin/logs/test/*.log | grep "Login" | sort | uniq -c | sort -nr -k1,2 >/home/admin/logs/test/shellOut.txt
 * <p>
 * <p>
 * TODO: 尚未明确执行要多线程运行
 *
 * @date 2018-12-22
 */
public class GrepSortInJava {

    static final Logger log = LoggerFactory.getLogger(GrepSortInJava.class);

    @Test
    public void test01() throws IOException, InterruptedException {

        String searchPath = "/home/admin/logs/test";
        String outFile = "/home/admin/logs/test/javaOut.txt";


        CountDownLatch latch = new CountDownLatch(1);

        Writer writer = new OutputStreamWriter(new FileOutputStream(outFile), StandardCharsets.UTF_8);


        Flux.fromStream(Files.walk(Paths.get(searchPath)))

                // 只处理 *.log 的普通文件
                .filter(path -> path.toFile().isFile() && path.toString().endsWith(".log"))

                // 变成 一行一行的 文本流
                .flatMap(path -> Flux.using(
                        () -> Files.lines(path, StandardCharsets.UTF_8),
                        Flux::fromStream,
                        BaseStream::close
                ))

                // 模拟 : `grep "Login"`
                .filter(line -> line.contains("Login"))

                // 模拟 : uniq -c
                .groupBy(Function.identity())
                .flatMapSequential(groupFlux -> {
                    //log.debug("flatMapSequential : " + groupFlux.key());
                    return groupFlux
                            .count()
                            .map(count -> {
                                log.debug("flatMapSequential.count : " + groupFlux.key());
                                return String.format("%4d", count) + " " + groupFlux.key();
                            });
                })

                // 排序
                .sort(Comparator
                        .comparingLong((String line) ->
                                NumberUtils.toLong(line.trim().split("\\s+")[0])
                        )
                        .thenComparing((String line) ->
                                NumberUtils.toLong(line.trim().split("\\s+")[1]))
                        .reversed()
                )

                .doFinally((signalType) -> {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    latch.countDown();
                })

                // 输出
                .subscribe(
                        (line) -> {
                            try {
                                writer.write(line + "\n");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        },
                        err -> log.error("Error occurred", err)
                )
        ;

        latch.await();
        log.info("Done");
    }


}
