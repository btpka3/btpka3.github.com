package me.test.jdk.java.lang;

import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author dangqian.zll
 * @date 2026/2/5
 */
@Slf4j
public class IterableTest {


    /**
     * 演示
     */
    public void typeChangeDemo() {
        // Iterable -> Iterator -> Spliterator
        {
            Iterable<String> it = Arrays.asList("aaa", "bbb", "ccc");
            Iterator<String> iterator = it.iterator();
            Spliterator<String> spliterator = Spliterators.spliteratorUnknownSize(iterator, 0);
        }

        // Iterable -> Enumeration // 自定义桥接类
        {
            Iterable<String> it = Arrays.asList("aaa", "bbb", "ccc");
            Enumeration<String> enumeration = new IterableEnumeration<>(it);
        }

        //  Enumeration->Iterable
        {
            Enumeration<String> enumeration = null;
            // 会提前获取所有元素, 不推荐
            Iterable<String> it1 = Collections.list(enumeration);
            // 推荐
            Iterable<String> it2 = () -> new EnumerationIterator<>(enumeration);
        }

        //  Enumeration->Iterator
        {
            Enumeration<String> enumeration = null;
            // 会提前获取所有元素, 不推荐。
            Iterator<String> iterator1 = new EnumerationIterator<>(enumeration);
            Iterator<String> iterator2 = Collections.list(enumeration).stream().iterator();
        }
    }


    @Test
    public void iterableTest01() {
        Iterable<String> it = Arrays.asList("aaa", "bbb", "ccc");
        for (String s : it) {
            log.info("----- ={}", s);
        }
    }

    /**
     * Iterable 是传统的顺序迭代接口，设计用于单线程按顺序遍历。
     */

    @Test
    public void iterator01() {
        AtomicInteger counter = new AtomicInteger(0);
        Iterable<String> it = Arrays.asList("aaa", "bbb", "ccc");
        Iterator<String> iterator = it.iterator();
        while (iterator.hasNext()) {
            log.info("-------- i={}", counter.getAndAdd(1));
            log.info("         next={}", iterator.next());
        }
    }

    /**
     * Spliterator 是Java 8 引入的可分割迭代接口，专为并行流和批量操作设计
     */
    @Test
    public void spliterator01() {
        Iterable<String> it = Arrays.asList("aaa", "bbb", "ccc");
        Spliterator<String> spliterator = it.spliterator();
        log.info("Characteristics: {}", spliterator.characteristics());
        log.info("Has ORDERED: {}", spliterator.hasCharacteristics(Spliterator.ORDERED));
        log.info("Has SIZED: {}", spliterator.hasCharacteristics(Spliterator.SIZED));
        log.info("Estimate size: {}", spliterator.estimateSize());

        log.info("\n使用 tryAdvance 遍历:");
        spliterator.forEachRemaining(log::info);


    }

    /**
     * 分割演示
     */
    @Test
    public void spliterator02() {
        Iterable<String> it = Arrays.asList("aaa", "bbb", "ccc", "ddd");
        {
            Spliterator<String> spliterator1 = it.spliterator();
            Spliterator<String> spliterator2 = spliterator1.trySplit();

            log.info("\n分割后第一部分:");
            spliterator1.forEachRemaining(log::info);

            log.info("\n分割后第二部分:");
            spliterator2.forEachRemaining(log::info);
        }

        // 5. 再次分割的例子
        {
            Spliterator<String> spliterator1 = it.spliterator();
            Spliterator<String> firstPart = spliterator1.trySplit();
            Spliterator<String> secondPart = spliterator1.trySplit();

            log.info("\n多次分割示例:");
            log.info("第一部分大小: {}", firstPart.estimateSize());
            log.info("第二部分大小: {}", secondPart.estimateSize());
            log.info("剩余部分大小: {}", spliterator1.estimateSize());

        }
    }

    static <T> Iterable<T> mergeIterablesByGuava(Iterable<T>... its) {
        return Iterables.concat(its);
    }

    /**
     *
     * @deprecated stream.iterator() 会先把所有元素都先收集好。
     */
    @Deprecated
    static <T> Iterable<T> mergeIterablesByStreamConcat0(Iterable<T>... its) {
        Iterator<T> iterator = Arrays.stream(its)
                .flatMap(it -> StreamSupport.stream(it.spliterator(), false))
                .iterator();
        return () -> iterator;
    }

    /**
     * 大模型帮改造的
     *
     * @param its
     * @param <T>
     * @return
     */
    static <T> Iterable<T> mergeIterablesByStreamConcat(Iterable<T>... its) {
        return () -> StreamSupport.stream(new LazyConcatSpliterator<>(its), false).iterator();
    }

    private static class LazyConcatSpliterator<T> implements Spliterator<T> {
        private final Iterable<T>[] iterables;
        private int currentIndex = 0;
        private Spliterator<T> currentSpliterator;

        @SafeVarargs
        public LazyConcatSpliterator(Iterable<T>... iterables) {
            this.iterables = iterables;
            if (iterables.length > 0) {
                this.currentSpliterator = iterables[0].spliterator();
            }
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> action) {
            while (true) {
                if (currentSpliterator != null && currentSpliterator.tryAdvance(action)) {
                    return true;
                }
                if (currentIndex < iterables.length - 1) {
                    currentIndex++;
                    currentSpliterator = iterables[currentIndex].spliterator();
                } else {
                    return false;
                }
            }
        }

        @Override
        public Spliterator<T> trySplit() {
            return null; // 不支持分割
        }

        @Override
        public long estimateSize() {
            return Long.MAX_VALUE;
        }

        @Override
        public int characteristics() {
            return ORDERED;
        }
    }

    static <T> Iterable<T> mergeIterablesByCustomIterable(Iterable<T>... its) {
        return new ConcatIterable<>(its);
    }

    @Test
    public void testExtIterable() {
        final List<String> result = new ArrayList<>();
        AtomicInteger counter = new AtomicInteger(0);
        Iterable<String> it = new ExtIterable<>(Arrays.asList("a1", "a2", "a3"), result::add);
        {
            log.info("====================== ExtIterable");
            for (String s : it) {
                log.info("-------- i={}, s={}", counter.getAndAdd(1), s);
            }
            Assertions.assertEquals(Arrays.asList("a1", "a2", "a3"), result);
            result.clear();
            counter.set(0);
        }
        {
            Spliterator<String> spliterator = it.spliterator();
            spliterator.forEachRemaining(s -> {
                log.info("-------- i={}, s={}", counter.getAndAdd(1), s);
            });
            Assertions.assertEquals(Arrays.asList("a1", "a2", "a3"), result);
            result.clear();
            counter.set(0);
        }
        {
            Spliterator<String> spliterator1 = it.spliterator();
            Spliterator<String> spliterator2 = spliterator1.trySplit();

            log.info("\n分割后第一部分:");
            spliterator1.forEachRemaining(s -> {
                log.info("-------- i={}, s={}", counter.getAndAdd(1), s);
            });

            log.info("\n分割后第二部分:");
            spliterator2.forEachRemaining(
                    s -> {
                        log.info("-------- i={}, s={}", counter.getAndAdd(1), s);
                    }
            );
            Assertions.assertEquals(Arrays.asList("a1", "a2", "a3"), result);
            result.clear();
            counter.set(0);
        }
    }

    @Test
    public void mergeIterables() {


        final List<String> result = new ArrayList<>();
        Consumer<String> action = s -> result.add("xx");

        //noinspection unchecked
        Iterable<String>[] its = new Iterable[]{
                new ExtIterable<>(Arrays.asList("a1", "a2", "a3"), result::add),
                new ExtIterable<>(Arrays.asList("b1", "b2", "b3"), result::add),
                new ExtIterable<>(Arrays.asList("c1", "c2", "c3"), result::add),
        };

        List<String> expected = Arrays.asList(
                "a1", "xx", "a2", "xx", "a3", "xx",
                "b1", "xx", "b2", "xx", "b3", "xx",
                "c1", "xx", "c2", "xx", "c3", "xx"
        );
        List<String> expected2 = Arrays.asList(
                "a1", "a2", "a3", "xx", "xx", "xx",
                "b1", "b2", "b3", "xx", "xx", "xx",
                "c1", "c2", "c3", "xx", "xx", "xx"
        );

        {
            AtomicInteger counter = new AtomicInteger(0);
            log.info("====================== mergeIterablesByGuava");
            Iterable<String> it = mergeIterablesByGuava(its);
            it = new ExtIterable<>(it, action);
            for (String s : it) {
                log.info("-------- i={}, s={}", counter.getAndAdd(1), s);
            }
            Assertions.assertEquals(expected, result);
            result.clear();
        }

        {
            AtomicInteger counter = new AtomicInteger(0);
            log.info("====================== mergeIterablesByStreamConcat0");
            Iterable<String> it = mergeIterablesByStreamConcat0(its);
            it = new ExtIterable<>(it, action);
            for (String s : it) {
                log.info("-------- i={}, s={}", counter.getAndAdd(1), s);
            }
            // ⚠️ 这里注意：由于 stream.iterator() 提前采集了相关结果，这里的顺序并不是交错的。
            Assertions.assertEquals(expected2, result);
            result.clear();
        }

        {
            AtomicInteger counter = new AtomicInteger(0);
            log.info("====================== mergeIterablesByStreamConcat");
            Iterable<String> it = mergeIterablesByStreamConcat(its);
            it = new ExtIterable<>(it, action);
            for (String s : it) {
                log.info("-------- i={}, s={}", counter.getAndAdd(1), s);
            }
            Assertions.assertEquals(expected, result);
            result.clear();
        }
        {
            AtomicInteger counter = new AtomicInteger(0);
            log.info("====================== mergeIterablesByCustomIterable");
            Iterable<String> it = mergeIterablesByCustomIterable(its);
            it = new ExtIterable<>(it, action);
            for (String s : it) {
                log.info("-------- i={}, s={}", counter.getAndAdd(1), s);
            }
            Assertions.assertEquals(expected, result);
            result.clear();
        }
    }

    public static class ConcatIterable<T> implements Iterable<T> {
        private final Iterable<T>[] iterables;

        @SafeVarargs
        public ConcatIterable(Iterable<T>... iterables) {
            this.iterables = iterables;
        }

        @NonNull
        @Override
        public Iterator<T> iterator() {
            return new ConcatIterator<>(iterables);
        }

        private static class ConcatIterator<T> implements Iterator<T> {
            private final Iterable<T>[] iterables;
            private int currentIterableIndex = 0;
            private Iterator<T> currentIterator;

            @SafeVarargs
            public ConcatIterator(Iterable<T>... iterables) {
                this.iterables = iterables;
                this.currentIterator = iterables[0].iterator();
            }

            @Override
            public boolean hasNext() {
                while (!currentIterator.hasNext() && currentIterableIndex < iterables.length - 1) {
                    currentIterableIndex++;
                    currentIterator = iterables[currentIterableIndex].iterator();
                }
                return currentIterator.hasNext();
            }

            @Override
            public T next() {
                return currentIterator.next();
            }
        }
    }


    public static class MyIterator<T> implements Iterator<T> {

        private final List<T> list;
        private int currentIndex = 0;

        public MyIterator(List<T> list) {
            this.list = list;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < list.size();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            return list.get(currentIndex++);
        }
    }

    public static class ExtIterable<T> implements Iterable<T> {
        private final Iterable<T> it;
        private final Consumer<T> action;

        public ExtIterable(Iterable<T> it, Consumer<T> action) {
            this.it = it;
            this.action = action;
        }

        @Override
        public @NonNull Iterator<T> iterator() {
            return new ExtIterator<>(it.iterator(), action);
        }

//        public Spliterator<T> spliterator() {
//            return Spliterators.spliteratorUnknownSize(iterator(), 0);
//        }
    }

    public static class ExtIterator<T> implements Iterator<T> {

        private final Iterator<T> iterator;
        private final Consumer<T> action;

        public ExtIterator(Iterator<T> iterator, Consumer<T> action) {
            this.iterator = iterator;
            this.action = action;
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public T next() {
            T v = iterator.next();
            try {
                action.accept(v);
            } catch (Exception e) {
                // NOOP
            }
            return v;
        }

        @Override
        public void remove() {
            iterator.remove();
        }

        // 不要这样实现，否则 action未执行，保持父类的默认实现就好
//        @Override
//        public void forEachRemaining(Consumer<? super T> action) {
//            iterator.forEachRemaining(action);
//        }

    }


    public static class EnumerationIterator<T> implements Iterator<T> {
        private final Enumeration<T> enumeration;

        public EnumerationIterator(Enumeration<T> enumeration) {
            this.enumeration = enumeration;
        }

        @Override
        public boolean hasNext() {
            return enumeration.hasMoreElements();
        }

        @Override
        public T next() {
            return enumeration.nextElement();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static class IterableEnumeration<T> implements Enumeration<T> {
        private final Iterator<T> iterator;

        public IterableEnumeration(Iterable<T> iterable) {
            this.iterator = iterable.iterator();
        }

        @Override
        public boolean hasMoreElements() {
            return iterator.hasNext();
        }

        @Override
        public T nextElement() {
            return iterator.next();
        }
    }

}