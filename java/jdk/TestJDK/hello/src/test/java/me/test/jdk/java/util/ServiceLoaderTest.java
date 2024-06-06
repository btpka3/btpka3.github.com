package me.test.jdk.java.util;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

/**
 * @author dangqian.zll
 * @date 2024/5/29
 */
public class ServiceLoaderTest {

    @Test
    public void test01() {
        ServiceLoader<MySpi> loader = ServiceLoader.load(MySpi.class);
        String name = "zhang3";
        List<String> resultList = loader.stream()
                .map(provider -> provider.get().sayHello(name))
                .collect(Collectors.toList());
        System.out.println("resultList = " + resultList);
    }
}
