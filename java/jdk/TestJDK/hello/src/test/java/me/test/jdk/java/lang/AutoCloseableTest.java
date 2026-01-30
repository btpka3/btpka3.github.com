package me.test.jdk.java.lang;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/5/9
 */
public class AutoCloseableTest {


    /**
     * 测试 AutoCloseable 的执行顺序。
     * <p>
     * 结论:
     * <ol>
     *     <li>{@link AutoCloseable#close()}</li>
     *     <li>catch block</li>
     *     <li>finally block</li>
     * </ol>
     */
    @SneakyThrows
    @Test
    public void test01() {
        log("test01: start");

        try (MyAutoCloseable myAutoCloseable = new MyAutoCloseable()) {
            Thread.sleep(100);
            throw new RuntimeException("test err");
        } catch (Exception e) {
            Thread.sleep(100);
            log("test01: catch : " + e.getMessage());
        } finally {
            Thread.sleep(100);
            log("test01: finally");
        }
        log("test01: end");
    }

    /**
     * 校验多个 AutoCloseable 时，其中一个 close 报错的行为表现。
     * <p>
     * 测试结论：多个 AutoCloseable 对象执行顺序不固定，有一个报错，就终止。
     *
     * <ul>
     *     <li>多个 AutoCloseable 对象执行顺序不固定，与变量声明的顺序无关，行为表现上感觉像是HashSet。</li>
     *     <li>多个 AutoCloseable 有一个报错，就跳出，不再执行剩余的 AutoCloseable#close()</li>
     *     <li>AutoCloseable#close()的报错可以被catch block 捕获。</li>
     * </ul>
     */
    @Test
    @SneakyThrows
    public void test02() {
        log("test02: start");

        try (
                MyAutoCloseable c3 = new MyAutoCloseable(3);
                MyAutoCloseable c0 = new MyAutoCloseable(0);
                MyAutoCloseable c1 = new MyAutoCloseable(1);
                MyAutoCloseable c2 = new MyAutoCloseable(2);
        ) {
            Thread.sleep(100);
        } catch (Exception e) {
            Thread.sleep(100);
            log("test02: catch : " + e.getMessage());
        }
        log("test02: end");
    }


    /**
     * 尝试自定义 集合类 来定制多个 AutoCloseable#close 的执行
     * <p>
     * 比如：执行顺序，多个异常的合并等。
     */
    @Test
    @SneakyThrows
    public void test03() {
        log("test03: start");

        try (
                MyAutoCloseableList<MyAutoCloseable> myAutoCloseableList = toAutoCloseableList(List.of(
                        new MyAutoCloseable(0),
                        new MyAutoCloseable(1),
                        new MyAutoCloseable(2),
                        new MyAutoCloseable(3)
                ))
        ) {
            Thread.sleep(100);
        } catch (Exception e) {
            Thread.sleep(100);
            log("test03: catch : " + e.getMessage());
        }
        log("test03: end");
    }

    public static class MyAutoCloseable implements AutoCloseable {

        AtomicInteger flag;

        public MyAutoCloseable() {
            this(new AtomicInteger(0));
        }

        public MyAutoCloseable(int f) {
            this(new AtomicInteger(f));
        }

        MyAutoCloseable(AtomicInteger flag) {
            this.flag = flag;
        }


        @Override
        public void close() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (flag.get() % 2 == 1) {
                throw new RuntimeException("MyAutoCloseable[" + flag.get() + "] close err");
            }
            log("MyAutoCloseable[" + flag.get() + "]: close() : " + System.currentTimeMillis());
        }
    }

    public static void log(String msg) {
        System.out.println(System.currentTimeMillis() + " : " + msg);
    }

    public interface MyAutoCloseableList<E> extends List<E>, AutoCloseable {
    }

    @SuppressWarnings("unchecked")
    public static <E extends AutoCloseable> MyAutoCloseableList<E> toAutoCloseableList(List<E> list) {
        return (MyAutoCloseableList<E>) Proxy.newProxyInstance(
                MyAutoCloseableList.class.getClassLoader(),
                new Class[]{MyAutoCloseableList.class},
                (proxy, method, args) -> {
                    if (Objects.equals("close", method.getName()) && (args == null || args.length == 0)) {
                        List<Exception> allExceptions = new ArrayList<>();
                        for (E item : list) {
                            try {
                                item.close();
                            } catch (Exception e) {
                                allExceptions.add(e);
                            }
                        }
                        if (!allExceptions.isEmpty()) {
                            String mergedErrMsg = allExceptions.stream()
                                    .map(Exception::getMessage)

                                    .collect(Collectors.joining(",", "【", "】"));
                            throw new RuntimeException("failed to autoclose : " + mergedErrMsg);
                        }
                        return null;
                    } else {
                        return method.invoke(list, args);
                    }
                }
        );
    }

}
