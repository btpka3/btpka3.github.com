package me.test.first.spring.boot.test;

import io.vavr.*;
import io.vavr.collection.Array;
import io.vavr.collection.CharSeq;
import io.vavr.collection.List;
import io.vavr.collection.Stream;
import io.vavr.concurrent.Future;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

import static io.vavr.Predicates.instanceOf;


/**
 * @author dangqian.zll
 * @date 2020/11/1
 * @see <a href="https://www.vavr.io/">vavr</a>
 * @see <a href="https://www.baeldung.com/vavr-future">Introduction to Future in Vavr</a>
 */
public class VavrTest {


    @Test
    public void join01() {

        String result = List.of("aa", "bb", "cc")
                .intersperse(", ")
                .foldLeft(new StringBuilder(), StringBuilder::append)
                .toString();

        Assertions.assertEquals("aa, bb, cc", result);
        Assertions.assertEquals("aa, bb, cc", List.of("aa", "bb", "cc").mkString(", "));
    }

    @Test
    public void tuple01() {
        Tuple2<String, Integer> java8 = Tuple.of("Java", 8);
        Assertions.assertEquals("Java", java8._1);
        Assertions.assertEquals(8, (Object) java8._2);

        {
            Tuple2<String, Integer> that = java8.map(
                    s -> s.substring(2) + "vr",
                    i -> i / 8
            );
            Assertions.assertEquals("vavr", that._1);
            Assertions.assertEquals(1, (Object) that._2);
        }
        {
            String that = java8.apply((s, i) -> s.substring(2) + "vr " + i / 8);
            Assertions.assertEquals("vavr1", that);
        }
    }

    @Test
    public void funcComposition01() {
        Function1<Integer, Integer> plusOne = a -> a + 1;
        Function1<Integer, Integer> multiplyByTwo = a -> a * 2;

        // andThen
        {
            Function1<Integer, Integer> add1AndMultiplyBy2 = plusOne.andThen(multiplyByTwo);
            Assertions.assertEquals(6, (int) add1AndMultiplyBy2.apply(2));
        }

        // compose
        {
            Function1<Integer, Integer> add1AndMultiplyBy2 = multiplyByTwo.compose(plusOne);
            Assertions.assertEquals(6, (int) add1AndMultiplyBy2.apply(2));
        }
    }


    @Test
    public void funcLifting01() {

        Function2<Integer, Integer, Integer> divide = (a, b) -> a / b;

        Function2<Integer, Integer, Option<Integer>> safeDivide = Function2.lift(divide);

        // = None
        Option<Integer> i1 = safeDivide.apply(1, 0);
        Assertions.assertTrue(i1.isEmpty());

        // = Some(2)
        Option<Integer> i2 = safeDivide.apply(4, 2);
        Assertions.assertFalse(i2.isEmpty());
        Assertions.assertEquals(2, (int) i2.get());
    }

    @Test
    public void funcPartialApplication02() {

        Function5<Integer, Integer, Integer, Integer, Integer, Integer> sum = (a, b, c, d, e) -> a + b + c + d + e;
        // 按顺序给前三个参数提供默认值
        Function2<Integer, Integer, Integer> add6 = sum.apply(2, 3, 1);

        Assertions.assertEquals(13, (int) add6.apply(4, 3));
    }

    @Test
    public void funcCurrying01() {
        Function2<Integer, Integer, Integer> sum = (a, b) -> a + b;

        {
            Function1<Integer, Integer> add2 = sum.curried().apply(2);
            Assertions.assertEquals(6, (int) add2.apply(4));
        }

    }

    @Test
    public void funcCurrying02() {

        Function3<Integer, Integer, Integer, Integer> sum = (a, b, c) -> a + b + c;
        final Function1<Integer, Function1<Integer, Integer>> add2 = sum.curried().apply(2);
        Assertions.assertEquals(9, (int) add2.apply(4).apply(3));
    }

    @Test
    public void funcCurrying03() {
        Function3<String, String, String, String> sum = (a, b, c) -> a + b + c;
        final Function1<String, Function1<String, String>> add2 = sum.curried().apply("2");

        Assertions.assertEquals("243", add2.apply("4").apply("3"));
    }


    @Test
    public void funcMemoize01() {
        Function0<Double> hashCache =
                Function0.of(Math::random).memoized();

        double randomValue1 = hashCache.apply();
        double randomValue2 = hashCache.apply();

        Assertions.assertEquals(randomValue1, randomValue2, 0);
    }


    public static class MyRuntimeException extends RuntimeException {

        public MyRuntimeException(String message) {
            super(message);
        }

    }


    protected String bunchOfWork(String a) {
        if (Objects.equals("aaa", a)) {
            throw new IllegalArgumentException("ILLEGAL_ARG_ERROR");

        }
        if (Objects.equals("bbb", a)) {
            throw new MyRuntimeException("MY_ERROR");
        }
        if (Objects.equals("ccc", a)) {
            throw new RuntimeException("UNKNOWN_ERROR");
        }

        if (Objects.equals("ddd", a)) {
            return null;
        }
        return "Hi," + a;
    }

    @Test
    public void try01() {
        Function1<String, String> wrappedFunc = (s) -> Try.of(() -> bunchOfWork(s))
                .recover(err -> API.Match(err).of(
                        API.Case(
                                API.$(instanceOf(IllegalArgumentException.class)),
                                t -> "RECOVER_1:" + s + ":" + t.getMessage()
                        ),
                        API.Case(
                                API.$(instanceOf(MyRuntimeException.class)),
                                t -> "RECOVER_2:" + s + ":" + t.getMessage()
                        ))
                )
                .getOrElse("RECOVER_3:" + s);

        Assertions.assertEquals("RECOVER_1:aaa:ILLEGAL_ARG_ERROR", wrappedFunc.apply("aaa"));
        Assertions.assertEquals("RECOVER_2:bbb:MY_ERROR", wrappedFunc.apply("bbb"));
        Assertions.assertEquals("RECOVER_3:ccc", wrappedFunc.apply("ccc"));
        Assertions.assertNull(wrappedFunc.apply("ddd"));
        Assertions.assertEquals("Hi,eee", wrappedFunc.apply("eee"));
    }


    @Test
    public void lazy01() {
        Lazy<Double> lazy = Lazy.of(Math::random);
        Assertions.assertFalse(lazy.isEvaluated());
        double result1 = lazy.get();
        Assertions.assertTrue(lazy.isEvaluated());
        double result2 = lazy.get();
        Assertions.assertEquals(result1, result2, 0);
    }

    @Test
    public void either01() {

    }


    @Test
    public void future01() {

        AtomicLong c = new AtomicLong(0);
        Supplier<String> supplier = () -> {
            long count = c.getAndAdd(1);
            if (count > 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "aaa-" + count;
        };


        {
            Future<String> resultFuture = Future.of(supplier::get)
                    .onSuccess(v -> System.out.println("Successfully Completed - Result: " + v))
                    .onFailure(v -> System.out.println("Failed - Result: " + v));

            long start = System.currentTimeMillis();
            String result = resultFuture.get();

            long end = System.currentTimeMillis();
            long cost = end - start;
            System.out.println("1. result = " + result + ",  cost=" + cost);
        }

        {
            Future<String> resultFuture = Future.of(supplier::get)
                    .onSuccess(v -> System.out.println("Successfully Completed - Result: " + v))
                    .onFailure(v -> System.out.println("Failed - Result: " + v));

            long start = System.currentTimeMillis();
            String result = resultFuture.get();


            long end = System.currentTimeMillis();
            long cost = end - start;
            System.out.println("2. result = " + result + ",  cost=" + cost + ", exception = " +
                    (resultFuture.isFailure() ? resultFuture.getCause().get() : null)
            );
        }
    }


    @Test
    public void validation01() {

    }

    @Test
    public void lis01() {
        Assertions.assertEquals(6, (int) List.of(1, 2, 3).sum());
    }


    @Test
    public void charSeq01() {
        Assertions.assertEquals(
                CharSeq.of('1', '2'),
                CharSeq.of('1', '2', '3').dropRightUntil(i -> i == '2'));
    }


    @Test
    public void stream01() {

        Array<Long> arr = Stream.from(1L)
                .filter(i -> i % 2L == 0)
                .take(3)
                .toArray();

        // 2,4,6

        Assertions.assertEquals(3, arr.size());
        Assertions.assertEquals(2, (long) arr.get(0));
        Assertions.assertEquals(4, (long) arr.get(1));
        Assertions.assertEquals(6, (long) arr.get(2));
    }


    @Test
    public void stream02() {

        List<String> list = List.of(
                "Java", "PHP", "Jquery", "JavaScript", "JShell", "JAVA");
        {
            // 删除前面的两个元素
            List list1 = list.drop(2);
            Assertions.assertFalse(list1.contains("Java") && list1.contains("PHP"));
        }
        {
            // 删除后面的两个元素
            List list2 = list.dropRight(2);
            Assertions.assertFalse(list2.contains("JAVA") && list2.contains("JShell"));
        }
        {
            // 删除前的元素，直到 找到一个元素 包含 "Shell"
            List list3 = list.dropUntil(s -> s.contains("Shell"));
            Assertions.assertEquals(2, list3.size());
            Assertions.assertEquals("JShell", list3.get(0));
            Assertions.assertEquals("JAVA", list3.get(1));
        }
        {
            // 理解起来有点绕：删除右边连续匹配的元素。
            List list4 = list.dropWhile(s -> s.contains("Java") || s.contains("PHP"));

            Assertions.assertEquals(4, list4.size());
            Assertions.assertEquals("Jquery", list4.get(0));
            Assertions.assertEquals("JavaScript", list4.get(1));
            Assertions.assertEquals("JShell", list4.get(2));
            Assertions.assertEquals("JAVA", list4.get(3));
        }
    }



}
