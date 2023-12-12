package me.test.jdk.java.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 *
 *
 * <p>
 * <code><pre>
 * LOOP=1000
 * SIZE=1024000
 * java SecureRandomLoadTest NativePRNG             "${LOOP}" "${SIZE}" | tee NativePRNG.log
 * java SecureRandomLoadTest NativePRNGBlocking     "${LOOP}" "${SIZE}" | tee NativePRNGBlocking.log
 * java SecureRandomLoadTest NativePRNGNonBlocking  "${LOOP}" "${SIZE}" | tee NativePRNGNonBlocking.log
 * java SecureRandomLoadTest DRBG                   "${LOOP}" "${SIZE}" | tee DRBG.log
 *
 * NativePRNG            : 13.23S
 * NativePRNGBlocking    : 无法工作
 * NativePRNGNonBlocking : 12.85S
 * DRBG                  :  4.62S
 * </pre></code>
 * <p>
 *
 * <p>
 * -Djava.security.egd=file:/dev/./urandom
 *
 * @see <a href=""></a>
 */
public class SecureRandomLoadTest {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String argo = args[0];
        int loop = Integer.valueOf(args[1]);
        int size = Integer.valueOf(args[2]);
        byte[] randomBytes = new byte[size];

        long total = 0L;
        for (int i = 0; i < loop; i++) {
            SecureRandom secureRandom = SecureRandom.getInstance(argo);
            long start = System.nanoTime();
            secureRandom.nextBytes(randomBytes);
            long end = System.nanoTime();
            long duration = end - start;
            total += duration;
            System.out.println(argo + " " + i + " " + duration);
        }
        System.out.println("total " + total);
    }
}
