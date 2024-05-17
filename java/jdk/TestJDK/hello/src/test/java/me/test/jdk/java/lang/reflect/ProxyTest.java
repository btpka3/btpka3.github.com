package me.test.jdk.java.lang.reflect;


import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author dangqian.zll
 * @date 2024/5/8
 */
public class ProxyTest {

    @Test
    public void test01() {

        Map<String, String> map = new HashMap<>(32);
        Object proxyObj = Proxy.newProxyInstance(
                ProxyTest.class.getClassLoader(),
                new Class[]{Map.class, MyInterface.class},
                new MyInvocationHandler(map)
        );

        assertInstanceOf(MyInterface.class, proxyObj);
        assertInstanceOf(Map.class, proxyObj);
        assertFalse(proxyObj instanceof HashMap);

        Map<String, String> proxyMap = (Map<String, String>) proxyObj;
        proxyMap.put("a", "aaa");

        assertEquals("my-aaa", proxyMap.get("a"));
        assertEquals("aaa", map.get("a"));
    }


    @SneakyThrows
    <T, V> V getFiled(Class<T> clazz, T obj, String filedName) {
        Field filed = clazz.getDeclaredField(filedName);
        filed.setAccessible(true);
        return (V) filed.get(obj);
    }

    /**
     * 测试代理对象是否能通过反射获取到原本的静态字段。
     * <p>
     * 注意：运行态单测case 需要增加以下JVM 参数
     * <pre{@code
     * --add-opens=java.base/java.util=ALL-UNNAMED
     * }</pre>
     * <p>
     * 备注：如果是 <a href="https://tomcat.apache.org/tomcat-10.1-doc/setup.html">tomcat 10.1</a> , 常见需要增加以下JVM 参数：
     * <pre>{@code
     * --add-opens=java.base/java.lang=ALL-UNNAMED \
     * --add-opens=java.base/java.io=ALL-UNNAMED \
     * --add-opens=java.base/java.util=ALL-UNNAMED \
     * --add-opens=java.base/java.util.concurrent=ALL-UNNAMED \
     * --add-opens=java.rmi/sun.rmi.transport=ALL-UNNAMED \
     * }</pre>
     */
    @Test
    public void test02() {

        HashMap<String, String> map = new HashMap<>(32);

        assertEquals(0.75f, (float) getFiled(HashMap.class, null, "DEFAULT_LOAD_FACTOR"));
        assertEquals(0.75f, (float) getFiled(HashMap.class, map, "loadFactor"));

        Object proxyObj = Proxy.newProxyInstance(
                ProxyTest.class.getClassLoader(),
                new Class[]{Map.class, MyInterface.class},
                new MyInvocationHandler(map)
        );
        assertInstanceOf(MyInterface.class, proxyObj);
        assertInstanceOf(Map.class, proxyObj);
        assertFalse(proxyObj instanceof HashMap);

        assertTrue(Proxy.isProxyClass(proxyObj.getClass()));
        MyInvocationHandler myInvocationHandler = (MyInvocationHandler) Proxy.getInvocationHandler(proxyObj);
        assertSame(map, myInvocationHandler.target);

        // 因为代理对象不是 HashMap，故不能获取到字段
        // assertEquals(0.75f, (float) getFiled(proxyObj.class, null, "DEFAULT_LOAD_FACTOR"));
        // assertEquals(0.75f, (float) getFiled(proxyObj.class, map, "loadFactor"));
    }


    public interface MyInterface {

    }

    public static class MyInvocationHandler implements InvocationHandler {

        Map<String, String> target;

        public MyInvocationHandler(Map<String, String> target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("get")) {

                Object originalValue = method.invoke(target, args);
                return "my-" + originalValue;
            }
            return method.invoke(target, args);
        }
    }

}
