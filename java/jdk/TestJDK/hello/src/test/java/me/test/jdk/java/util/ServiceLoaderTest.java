package me.test.jdk.java.util;

import java.util.List;
import java.util.ServiceLoader;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/5/29
 */
public class ServiceLoaderTest {

    protected void load(ServiceLoader<MySpi> loader) {
        String name = "zhang3";
        List<String> resultList = loader.stream()
                .map(provider -> {
                    //  调用一次get方法就会触发new一个对象实例。
                    MySpi impl = provider.get();
                    System.out.println("=====" + impl.getClass() + "@" + System.identityHashCode(impl));
                    return impl.sayHello(name);
                })
                .toList();
        System.out.println("resultList = " + resultList);
    }

    @Test
    public void test01() {
        ServiceLoader<MySpi> loader = ServiceLoader.load(MySpi.class);
        load(loader);
        load(loader);
    }
}
