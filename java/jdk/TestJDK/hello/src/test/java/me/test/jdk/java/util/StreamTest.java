package me.test.jdk.java.util;

import org.apache.commons.collections4.MapUtils;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author dangqian.zll
 * @date 2020/10/26
 */
public class StreamTest {

    @Test
    public void testNullValues01() {

        List<Integer> list = Arrays.asList(101, 102, 103, 104, 105);
        List<String> newList = list.stream()
                .map(i -> (i % 2) == 0 ? null : "" + i)
                .collect(Collectors.toList());

        System.out.println(newList);
    }


    @Test
    public void treeToStreamDfs() {
        treeToStreamDfs(
                getDemoTree(),
                (Node node) -> node.nodes == null
                        ? Stream.empty()
                        : node.nodes.stream()
        )
                .forEach(node -> System.out.println(node.name));
    }


    @Test
    public void treeToStreamBfs() {
        treeToStreamBfs(
                getDemoTree(),
                (Node node) -> node.nodes == null
                        ? Stream.empty()
                        : node.nodes.stream()
        )
                .get()
                .forEach(node -> System.out.println(node.name));
    }

    protected Node getDemoTree() {


        Node a011 = new Node();
        a011.name = "a011";

        Node a012 = new Node();
        a012.name = "a012";


        Node a01 = new Node();
        a01.name = "a01";
        a01.nodes = Arrays.asList(a011, a012);


        Node a021 = new Node();
        a021.name = "a021";

        Node a022 = new Node();
        a022.name = "a022";

        Node a02 = new Node();
        a02.name = "a02";
        a02.nodes = Arrays.asList(a021, a022);


        Node a0 = new Node();

        a0.name = "a0";
        a0.nodes = Arrays.asList(a01, a02);


        return a0;
    }

    /**
     * 深度优先 (Depth First Search)
     *
     * @param node
     * @return
     */
    protected <T> Stream<T> treeToStreamDfs(T node, Function<T, Stream<T>> nextLevelDataGenerator) {
        return Stream.concat(
                Stream.of(node),
                nextLevelDataGenerator.apply(node)
                        .map(childNode -> treeToStreamDfs(childNode, nextLevelDataGenerator))
                        .reduce(Stream.empty(), Stream::concat)
        );
    }


    /**
     * 广度优先(Breadth FirstSearch)
     *
     * @param node
     * @return
     */
    protected <T> Supplier<Stream<T>> treeToStreamBfs(
            T node,
            Function<T, Stream<T>> nextLevelDataGenerator
    ) {
        Supplier<Stream<T>> all = () -> Stream.of(node);
        Supplier<Stream<T>> n = all;

        return getAll(all, n, nextLevelDataGenerator);
    }


    protected <T> Supplier<Stream<T>> getAll(
            Supplier<Stream<T>> all,
            Supplier<Stream<T>> n,
            Function<T, Stream<T>> nextLevelDataGenerator
    ) {
        // 下一层级的所有数据
        Supplier<Stream<T>> n1 = () -> n.get().flatMap(nextLevelDataGenerator);
        if (n1.get().findFirst().isPresent()) {
            return getAll(
                    () -> Stream.concat(all.get(), n1.get()),
                    n1,
                    nextLevelDataGenerator
            );
        } else {
            return () -> Stream.concat(all.get(), n1.get());
        }
    }


    public static class Node {
        String name;
        List<Node> nodes;
    }

    public static class User {
        private String name;
        private Integer age;

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    @Test
    public void groupBy01() {
        List<User> list = new ArrayList<>();
        {
            User user = new User();
            user.name = "zhang3";
            user.age = 10;
            list.add(user);
        }
        {
            User user = new User();
            user.name = "li4";
            user.age = 11;
            list.add(user);
        }
        {
            User user = new User();
            user.name = "wang5";
            user.age = 20;
            list.add(user);
        }
        {
            User user = new User();
            user.name = "zhao6";
            user.age = 21;
            list.add(user);
        }
        {
            User user = new User();
            user.name = "qian7";
            user.age = 30;
            list.add(user);
        }

        Map<Integer, List<User>> map = list.stream()
                .collect(Collectors.groupingBy(
                        (User user) -> {
                            return user.age == null ? (Integer) null : (Integer) (user.age / 10);
                        },
                        Collectors.toList()
                ));
        System.out.println(map);
    }


    @Test
    public void testGeneric01() {

        List<Map> aaa = new ArrayList<>(4);
        {
            Map m = new HashMap(4);
            m.put("value", "v1");
            aaa.add(m);
        }
        {
            Map m = new HashMap(4);
            m.put("value", "v2");
            aaa.add(m);
        }
        List<String> result = aaa.stream()
                .map((Function<Map, String>) rec -> MapUtils.getString(rec, "value"))
                .collect(Collectors.toList());

        System.out.println(result);
    }

//    @Test
//    public void testGeneric02() {
//
//        List<Map<String, Object>> aaa = new ArrayList<>(4);
//        {
//            Map m = new HashMap(4);
//            m.put("value", "v1");
//            aaa.add(m);
//        }
//        {
//            Map m = new HashMap(4);
//            m.put("value", "v2");
//            aaa.add(m);
//        }
//        List<String> result = aaa.stream()
//                // 编译错误 , 参考 testGeneric01()
//                // java: incompatible types: java.lang.Object cannot be converted to java.util.List<java.lang.String>
//                .<String>map((Map rec) -> (String) MapUtils.getString(rec, "value"))
//                .map(String.class::cast)
//                .collect(Collectors.toList());
//
//        System.out.println(result);
//    }

}
