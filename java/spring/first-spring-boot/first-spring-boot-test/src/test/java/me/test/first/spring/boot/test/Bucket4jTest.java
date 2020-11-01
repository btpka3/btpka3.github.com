package me.test.first.spring.boot.test;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author dangqian.zll
 * @date 2020/10/31
 * @see <a href="https://www.baeldung.com/spring-bucket4j">Rate Limiting a Spring API Using Bucket4j</a>
 */
public class Bucket4jTest {


    /**
     * 1分钟10次请求，但容易 这10个请求都在前两秒处理。
     */
    @Test
    public void test01() {
        Refill refill = Refill.intervally(10, Duration.ofMinutes(1));
        Bandwidth limit = Bandwidth.classic(10, refill);
        Bucket bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();

        for (int i = 1; i <= 10; i++) {
            assertTrue(bucket.tryConsume(1));
        }
        Assert.assertFalse(bucket.tryConsume(1));

    }

    /**
     * 2秒1个请求
     */
    @Test
    public void test02() {
        Bandwidth limit = Bandwidth.classic(1, Refill.intervally(1, Duration.ofSeconds(2)));
        Bucket bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();

        // 第一次： ok
        assertTrue(bucket.tryConsume(1));
        // 第二个次： 过快，false
        assertFalse(bucket.tryConsume(1));

        // 第三次：等待2秒，ok
        Executors.newScheduledThreadPool(1)   // schedule another request for 2 seconds later
                .schedule(
                        () -> assertTrue(bucket.tryConsume(1)),
                        2,
                        TimeUnit.SECONDS
                );
    }

    /**
     * 模拟消息处理：
     * 假设消息接收 为10个线程，但
     * 假设单个消息平局处理需要 50ms （全部是IO等待时间，非CPU时间）, 单个线程1秒处理 1000/50 = 20个消息
     * 如果要 100 qps, 则需要 100/20 = 5 个线程
     */
    @Test
    public void test03() {

        Executors.newScheduledThreadPool(5)   // schedule another request for 2 seconds later
                .schedule(
                        () -> {
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        },
                        2,
                        TimeUnit.SECONDS
                );

        Runnable msgListener = () -> {

        };


        Bandwidth limit = Bandwidth.classic(20, Refill.intervally(20, Duration.ofSeconds(50)));
        Bucket bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();

        // FIXME: 如何 retry ? 如何 同步等待？
     }


}
