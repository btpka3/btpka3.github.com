package me.test.jdk.java.util.function;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @FunctionalInterface
 */
public class FunctionTest {


    public static interface Aaa {
        String sayHello(String s1, String s2);
    }

    public static interface Bbb {
        String bbb();
    }


    static class Person {
        String name = "N/A";
        Integer age = 0;

        Person() {

        }

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    static class ComparisonProvider {
        public int compareByName(Person a, Person b) {
            return a.name.compareTo(b.name);
        }

        public int compareByAge(Person a, Person b) {
            return a.age.compareTo(b.age);
        }
    }


    public static void main(String[] args) {
//        test00();
//        test01();
//        test02();
//        test03();
        test04();
    }

    public static String hi() {
        return "hi";
    }

    public static String hi(String name) {
        return "hi " + name;
    }

    public static String hello(String user1, String user2) {
        return "hi " + user1 + " and " + user2;
    }

    int num = 10;

    public int addAndGetNum(int count) {
        num += count;
        return num;
    }

    public static void test00() {
        System.out.println("===================== test00: Reference to a static method (0 param)");

        // Tip : 也可以使用 java.util.function.Supplier
        // me.test.jdk.java.util.function.FunctionTest$$Lambda$1/381259350@4f3f5b24
        Bbb f = FunctionTest::hi;
        System.out.println(f);

        // // [interface me.test.jdk.java.util.function.FunctionTest$Bbb]
        System.out.println(Arrays.asList(f.getClass().getInterfaces()));

        // []
        System.out.println(Arrays.asList(f.getClass().getAnnotations()));

        // true
        System.out.println(f instanceof Function);

        // hi zhang3
        System.out.println(f.bbb());
    }

    public static void test01() {
        System.out.println("===================== test01: Reference to a static method (1 param)");

        // me.test.jdk.java.util.function.FunctionTest$$Lambda$1/381259350@4f3f5b24
        Function<String, String> f = FunctionTest::hi;
        System.out.println(f);

        // [interface java.util.function.Function]
        System.out.println(Arrays.asList(f.getClass().getInterfaces()));

        // []
        System.out.println(Arrays.asList(f.getClass().getAnnotations()));

        // true
        System.out.println(f instanceof Function);

        // hi
        System.out.println(f.apply("zhang3"));
    }


    public static void test02() {
        System.out.println("===================== test02 : Reference to a static method (2 param)");

        // 不能直接赋值给 Object 类型的变量，因为不是 Functional Interface。
        Aaa f = FunctionTest::hello;

        // me.test.jdk.java.util.function.FunctionTest$$Lambda$2/1791741888@5f184fc6
        System.out.println(f);

        // [interface me.test.jdk.java.util.function.FunctionTest$Aaa]
        System.out.println(Arrays.asList(f.getClass().getInterfaces()));

        // []
        System.out.println(Arrays.asList(f.getClass().getAnnotations()));

        // false
        System.out.println(f instanceof Function);

        // hi zhang3 and li4
        System.out.println(f.sayHello("zhang3", "li4"));
    }

    public static void test03() {
        System.out.println("===================== test03 : Reference to an instance method of a particular object");
        ComparisonProvider myComparisonProvider = new ComparisonProvider();
        Comparator<Person> c = myComparisonProvider::compareByName;
        Person[] personArr = {
                new Person("zhang3", 23),
                new Person("li4", 13),
                new Person("wang5", 33),
        };


        Arrays.sort(personArr, c);

        // [Person{name='li4', age=13}, Person{name='wang5', age=33}, Person{name='zhang3', age=23}]
        System.out.println(Arrays.asList(personArr));
    }

    public static void test04() {
        System.out.println("===================== test04 : Reference to a constructor");

        Supplier s = Person::new;

        // Person{name='N/A', age=0}
        System.out.println(s.get());
    }
}
