package me.test.jdk.java.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.MapUtils;
import org.junit.Test;

import java.io.Serializable;
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

    @Data
    @Builder(toBuilder = true)
    @AllArgsConstructor
    @NoArgsConstructor
    public static class User {
        private String room;
        private String name;
        private Integer age;
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

    /**
     * groupBy + orderBy + limit (FIXME: 未实现）
     */
    @Test
    public void groupBy02() {
        List<User> list = Arrays.asList(
                User.builder().room("room1").name("zhang3").age(3).build(),
                User.builder().room("room2").name("li4").age(4).build(),
                User.builder().room("room1").name("wang5").age(5).build(),
                User.builder().room("room2").name("zhao6").age(6).build(),
                User.builder().room("room1").name("sun7").age(7).build(),
                User.builder().room("room2").name("qian8").age(8).build(),
                User.builder().room("room1").name("wu9").age(9).build(),
                User.builder().room("room2").name("zheng10").age(10).build()
        );
        Collections.shuffle(list);

        Map<String, List<User>> map = list.stream()
                .collect(Collectors.groupingBy(
                        User::getRoom,
                        Collectors.toList()
                ));
        for (List<User> l : map.values()) {
            Collections.sort(l, Comparator.comparing(User::getAge));
            if (l.size() > 2) {
                // sublist 的修改会反应到原始list中
                l.subList(2, l.size()).clear();
            }
        }
        System.out.println(JSON.toJSONString(map, JSONWriter.Feature.PrettyFormat));
    }

    /**
     *
     */
    @Test
    public void groupBy03() {
        List<User> list = Arrays.asList(
                User.builder().room("room1").name("zhang3").age(3).build(),
                User.builder().room("room1").name("li4").age(3).build(),
                User.builder().room("room1").name("wang5").age(3).build(),
                User.builder().room("room1").name("zhao6").age(5).build(),
                User.builder().room("room2").name("sun7").age(5).build(),
                User.builder().room("room2").name("qian8").age(5).build(),
                User.builder().room("room2").name("wu9").age(6).build(),
                User.builder().room("room2").name("zheng10").age(6).build()
        );
        Collections.shuffle(list);

        Map<String, Map<Integer, List<User>>> map = list.stream()
                .collect(Collectors.groupingBy(
                        User::getRoom,
                        Collectors.groupingBy(User::getAge, Collectors.toList())
                ));

        System.out.println(JSON.toJSONString(map, JSONWriter.Feature.PrettyFormat));
    }

    /**
     * FIXME: Collectors.reducing
     */
    @Test
    public void groupBy04() {
        List<JarResult> list = Arrays.asList(
                JarResult.builder().app("app1").jar("jar1").loadedClassCount(3).build(),
                JarResult.builder().app("app1").jar("jar2").loadedClassCount(4).build(),
                JarResult.builder().app("app1").jar("jar3").loadedClassCount(5).build(),
                JarResult.builder().app("app2").jar("jar1").loadedClassCount(6).build(),
                JarResult.builder().app("app2").jar("jar2").loadedClassCount(7).build(),
                JarResult.builder().app("app3").jar("jar2").loadedClassCount(8).build()
        );
        Collections.shuffle(list);

//        Object o = list.stream()
//                .map(jarResult -> JarCountInfo.builder()
//                        .jar(jarResult.getJar())
//                        .loadedClassCounts(Arrays.asList(jarResult.getLoadedClassCount()))
//                        .build())
//                .collect(Collectors.reducing(
//                        JarCountInfo.builder().build(),
//                        jarResult -> JarCountInfo.builder().build(),
//                        (info1, info2) -> {
//                            info1.getLoadedClassCounts().addAll(info1.getLoadedClassCounts());
//                            return info1;
//                        }));
//        Object o = list.stream()
//                .collect(Collectors.groupingBy(
//                        JarResult::getJar,
//                        Collectors.mapping(
//                                jarResult -> JarCountInfo.builder()
//                                        .jar(jarResult.getJar())
//                                        .loadedClassCounts(Arrays.asList(jarResult.getLoadedClassCount()))
//                                        .build(),
//                                Collectors.reducing(
//                                        JarCountInfo.builder().build(),
//                                        jarResult -> JarCountInfo.builder().build(),
//                                        (info1, info2) -> {
//                                            info1.getLoadedClassCounts().addAll(info1.getLoadedClassCounts());
//                                            return info1;
//                                        })
//                        )
//
//                ));

//        System.out.println(JSON.toJSONString(o, JSONWriter.Feature.PrettyFormat));
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JarCountInfo implements Serializable {
        private String jar;
        private List<Integer> loadedClassCounts;
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static  class JarResult implements Serializable {
        private String app;
        private String jar;
        private Integer loadedClassCount;
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
