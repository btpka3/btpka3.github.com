package me.test.org.apache.commons.lang3;

import com.fasterxml.uuid.Generators;
import com.github.f4b6a3.uuid.UuidCreator;
import io.azam.ulidj.ULID;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/12/27
 * @see <a href="https://github.com/cowtowncoder/java-uuid-generator">Java Uuid Generator (JUG)</a>
 * @see <a href="https://github.com/f4b6a3/uuid-creator">uuid-creator</a>
 * @see <a href="https://github.com/azam/ulidj?tab=readme-ov-file">ulidj</a>
 * @see <a href="https://github.com/ulid/spec">ulid</a>
 */
public class RandomStringUtilsTest {

    static final String RANDOM_ALPHA_NUMERIC = "RANDOM_ALPHA_NUMERIC";
    static final String RANDOM_UUID = "RANDOM_UUID";
    static final String RANDOM_ULID = "RANDOM_ULID";
    static final String UUID_CREATOR_FAST = "UUID_CREATOR_FAST";
    static final String UUID_CREATOR_V7 = "UUID_CREATOR_V7";
    static final String JUG_V7_PLUS = "JUG_V7_PLUS";

    @Test
    public void test() {
        for (int i = 0; i < 10; i++) {
            System.out.println(RandomStringUtils.randomAlphanumeric(16));
        }
        for (int i = 0; i < 10; i++) {
            System.out.println(UUID.randomUUID());
        }
    }

    @Test
    public void vsUuid() {

        int count = 1000;
        {
            long start = System.nanoTime();
            for (int i = 0; i < count; i++) {
                randomAlphanumeric();
            }

            long end = System.nanoTime();
            long cost = end - start;
            System.out.println("RANDOM_1 : " + cost + " ns, avg=" + (cost / count) + " ns");
        }
        {
            long start = System.nanoTime();
            for (int i = 0; i < count; i++) {
                randomUUID();
            }
            long end = System.nanoTime();
            long cost = end - start;
            System.out.println("RANDOM_2 : " + cost + " ns, avg=" + (cost / count) + " ns");
        }
    }

    @Test
    public void testConcurrence() {
        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("cores = " + cores);

        run(RANDOM_UUID);
        run(RANDOM_ALPHA_NUMERIC);
        run(RANDOM_ULID);
        run(UUID_CREATOR_FAST);
        run(UUID_CREATOR_V7);
        run(JUG_V7_PLUS);
    }

    protected void noop() {}

    protected void randomUUID() {
        UUID.randomUUID().toString();
    }

    protected void randomAlphanumeric() {
        RandomStringUtils.randomAlphanumeric(16);
    }

    protected void randomULID() {
        ULID.random(ThreadLocalRandom.current());
    }

    protected void uuidCreatorFast() {
        UuidCreator.getRandomBasedFast().toString();
    }

    protected void uuidCreatorV7() {
        UuidCreator.getTimeOrderedEpoch().toString();
    }

    protected void jugV7Plus() {
        Generators.timeBasedEpochRandomGenerator().generate().toString();
    }

    final Map<String, Runnable> RUNABLES = Map.of(
            RANDOM_ALPHA_NUMERIC, this::randomAlphanumeric,
            RANDOM_UUID, this::randomUUID,
            RANDOM_ULID, this::randomULID,
            UUID_CREATOR_FAST, this::uuidCreatorFast,
            UUID_CREATOR_V7, this::uuidCreatorV7,
            JUG_V7_PLUS, this::jugV7Plus);

    @SneakyThrows
    public void run(String mode) {
        int cores = Runtime.getRuntime().availableProcessors();
        int count = 10000;
        int loop = 10000;

        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(loop);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(cores, cores, Long.MAX_VALUE, TimeUnit.SECONDS, workQueue);
        long begin = System.nanoTime();
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(loop);

        Runnable runnable = RUNABLES.get(mode);
        Assertions.assertNotNull(runnable, "mode=" + mode + " not found");

        for (int i = 0; i < loop; i++) {
            executor.submit(() -> {
                try {
                    startSignal.await();
                    for (int j = 0; j < count; j++) {
                        runnable.run();
                    }
                } catch (Throwable e) {

                } finally {
                    doneSignal.countDown();
                }
            });
        }
        startSignal.countDown();
        doneSignal.await();
        long end = System.nanoTime();
        long total = end - begin;
        long avg = total / (count * loop);
        System.out.printf("mode : %-24s , total: %18d ns, avg : %6d ns %n", mode, total, avg);
        executor.shutdown();
    }
}
