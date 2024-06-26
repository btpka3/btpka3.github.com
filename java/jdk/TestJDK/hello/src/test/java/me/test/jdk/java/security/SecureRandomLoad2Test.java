package me.test.jdk.java.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * 当有设置 JDK JVM属性 ： `-Djava.security.egd=file:/dev/./urandom` 时，SecureRandom 默认使用 DRBG 算法，否则默认使用 NativePRNG
 *
 * <p>
 * <code><pre>
 * LOOP=1000
 * SIZE=1024000
 * MODE=single
 * java SecureRandomLoad2Test NativePRNG             "${LOOP}" "${SIZE}" "${MODE}" | tee NativePRNG.${MODE}.log
 * java SecureRandomLoad2Test NativePRNGBlocking     "${LOOP}" "${SIZE}" "${MODE}" | tee NativePRNGBlocking.${MODE}.log
 * java SecureRandomLoad2Test NativePRNGNonBlocking  "${LOOP}" "${SIZE}" "${MODE}" | tee NativePRNGNonBlocking.${MODE}.log
 * java SecureRandomLoad2Test DRBG                   "${LOOP}" "${SIZE}" "${MODE}" | tee DRBG.${MODE}.log
 *                         MacOS(多实例) AliOS7(multiple)    AliOS7(single)
 * NativePRNG            : 14.17S       8.01S               7.84S
 * NativePRNGBlocking    : 15.17S       -                   -
 * NativePRNGNonBlocking : 15.73S       8.04S               7.88S
 * DRBG                  :  1.99S       0.93S               4.62
 *
 * # Mean   :  6.6519
 * # Std Dev: 16.2961
 * grep -v cores DRBG.multiple.log  | grep -v total | awk '{print $3/1000000.0}' | gnuplot -e 'stats "-"'
 * # Mean   : 36.0616
 * # Std Dev: 16.1979
 * grep -v cores DRBG.single.log    | grep -v total | awk '{print $3/1000000.0}' | gnuplot -e 'stats "-"'
 *
 * </pre></code>
 * <p>
 *
 * <p>
 * 配合 JVM 属性 -Djava.security.egd=file:/dev/./urandom， 发现 java.util.UUID#randomUUID() 使用DRBG算法，并且高并发时会被 BLOCK.
 * 原因是 UUID 使用了内部单例的一个 SecureRandom 对象，当使用 DRBG 算法时，效果较差。相比多实例 SecureRandom
 * DRBG 算法的 SecureRandom 多实例性能 大约是 单实例的 性能的5倍。
 * 多实例时，获取随机数耗时都比较平均，而单实例时会有大量长耗时（锁竞争）：
 * sun.security.provider.AbstractDrbg#generateAlgorithm(byte[], byte[]) 的所有实现都是 synchronized。
 *
 * 想法：应该可以通过 java-agent 机制，针对使用 DRBG 算法时， 修改 java.util.UUID#randomUUID() 方法调用前，
 * 都将 java.util.UUID.Holder#numberGenerator 设置成一个新对象/或者对象池里无人使用的对对象。
 *
 * @see sun.security.provider.AbstractDrbg#generateAlgorithm(byte[], byte[])
 * @see java.util.UUID#randomUUID()
 */
public class SecureRandomLoad2Test {

    public static void main(String[] args) throws NoSuchAlgorithmException, InterruptedException {

        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("cores = " + cores);
        String argo = args[0];
        int loop = Integer.valueOf(args[1]);
        int size = Integer.valueOf(args[2]);
        String mode = args[3]; // single/multiple

        byte[] randomBytes = new byte[size];

        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(loop);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(cores, cores, Long.MAX_VALUE, TimeUnit.SECONDS, workQueue);
        long begin = System.nanoTime();
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(loop);
        SecureRandom secureRandomGlobal = SecureRandom.getInstance(argo);
        for (int i = 0; i < loop; i++) {
            int idx = i;
            executor.submit(() -> {
                try {
                    SecureRandom secureRandom = Objects.equals("multiple", mode)
                            ? SecureRandom.getInstance(argo)
                            : secureRandomGlobal;
                    long start = System.nanoTime();
                    secureRandom.nextBytes(randomBytes);
                    long end = System.nanoTime();
                    long duration = end - start;
                    System.out.println(argo + " " + idx + " " + duration);
                } catch (Throwable e) {

                } finally {
                    doneSignal.countDown();

                }
            });
        }
        startSignal.countDown();
        doneSignal.await(30, TimeUnit.SECONDS);
        long end = System.nanoTime();
        long total = end - begin;
        System.out.println("total " + total);
        executor.shutdown();
    }
}
