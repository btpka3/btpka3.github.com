package me.test.jdk.java.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.concurrent.*;

/**
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
 * </pre></code>
 * <p>
 *
 * <p>
 * -Djava.security.egd=file:/dev/./urandom
 *
 * @see <a href=""></a>
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
