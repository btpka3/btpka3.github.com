package me.test.jdk.java.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 *
 * @author dangqian.zll
 * @date 2026/2/5
 * @see me.test.jdk.java.lang.IterableTest
 */
@Slf4j
public class EnumerationTest {
    @Test
    public void list2Enumeration() {
        Enumeration<String> e = Collections.enumeration(Arrays.asList("a1", "a2", "a3"));
        AtomicInteger counter = new AtomicInteger(0);
        while (e.hasMoreElements()) {
            log.info("-------- i={}", counter.getAndAdd(1));
            log.info("         next={}", e.nextElement());
        }
    }

    @Test
    public void iterable2Enumeration() {
        Enumeration<String> e = enumeration(Arrays.asList("a1", "a2", "a3"));
        AtomicInteger counter = new AtomicInteger(0);
        while (e.hasMoreElements()) {
            log.info("-------- i={}", counter.getAndAdd(1));
            log.info("         next={}", e.nextElement());
        }
    }

    /**
     * @see java.util.Collections#enumeration(java.util.Collection)
     */
    public static <T> Enumeration<T> enumeration(final Iterable<T> c) {
        return new Enumeration<>() {
            private final Iterator<T> i = c.iterator();

            public boolean hasMoreElements() {
                return i.hasNext();
            }

            public T nextElement() {
                return i.next();
            }
        };
    }

}