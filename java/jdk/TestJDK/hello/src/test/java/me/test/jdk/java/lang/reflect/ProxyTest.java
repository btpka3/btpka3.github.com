package me.test.jdk.java.lang.reflect;


import org.junit.jupiter.api.Test;

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
