package me.test.jdk.java.lang;

/**
 *
 * The Advantages and Traps of Autoboxing
 * <p>
 * 优点：
 * 1. 代码更简洁
 * 2. 自动使用最优的转换代码（比如使用Integer.valueOf(int)，而不是使用new Integer(int)）
 * <p>
 * 缺点:
 * 1. 如果不注意，可能会导致错误
 * 2. 如果不注意，可能会导致效率低下
 * 3. 有时需要强制类型转换。
 * <p>
 * 参考：http://javaeffective.wordpress.com/2010/05/21/the-advantages-and-traps-of-autoboxing/
 *
 */
public class Autoboxing {

    public static void main(String[] args) {

        // 导致错误
        Long longValue1 = 99L;
        System.out.println(longValue1.equals(99L)); // true
        System.out.println(longValue1.equals(99)); // false
        System.out.println(longValue1 == 99L); // true
        System.out.println(longValue1 == 99); // true

        long t1;
        long t2;
        // 导致效率低下
        // 实验组 1
        t1 = System.currentTimeMillis();
        Long counter1 = 0L; // Long counter = 0; 会报错！！！
        for (long i = 0; i < 100000000; i++) {
            counter1++; // 等价于 Long.valueOf(counter1.longValue() + 1)
        }
        t2 = System.currentTimeMillis();
        System.out.println("time1 = " + (t2 - t1)); // 1625

        // 实验组 2
        t1 = System.currentTimeMillis();
        long counter2 = 0;
        for (long i = 0; i < 100000000; i++) {
            counter2++;
        }
        t2 = System.currentTimeMillis();
        System.out.println("time2 = " + (t2 - t1)); // 297

        // Integer : 1 + 2 = 3
        // 需强制类型转换，否则调用printSum(long, long)
        // 强制转换为 int也会调用printSum(long, long)
        printSum((Integer) 1, (Integer) 2);

        // Long1 : 1 + 2 = 3
        printSum(1L, 2L);

        // 需强制类型转换
        // Long2 : 1 + 2 = 3
        printSum((Long) 1L, (Long) 2L);

        // Float : 1.0 + 2.0 = 3.0
        printSum(1F, 2F);

        // Double : 1.0 + 2.0 = 3.0
        printSum(1D, 2D);
    }

    public static void printSum(long a, long b) {
        System.out.println("Long1 : " + a + " + " + b + " = " + (a + b)); // true
    }

    public static void printSum(Long a, Long b) {
        System.out.println("Long2 : " + a + " + " + b + " = " + (a + b)); // true
    }

    public static void printSum(Integer a, Integer b) {
        System.out.println("Integer : " + a + " + " + b + " = " + (a + b)); // true
    }

    public static void printSum(Float a, Float b) {
        System.out.println("Float : " + a + " + " + b + " = " + (a + b)); // true
    }

    public static void printSum(Double a, Double b) {
        System.out.println("Double : " + a + " + " + b + " = " + (a + b)); // true
    }
}
