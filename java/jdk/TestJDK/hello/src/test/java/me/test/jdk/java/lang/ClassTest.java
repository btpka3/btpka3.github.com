package me.test.jdk.java.lang;

import java.lang.reflect.Field;
import java.util.Deque;
import java.util.Map;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.slf4j.spi.MDCAdapter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * @author dangqian.zll
 * @date 2024/5/8
 */
public class ClassTest {

    @Test
    public void testSetStaticField() {
        MDCAdapter value2 = new NoopMDCAdapter();
        MDCAdapter value1 = (MDCAdapter) setStaticFiled(MDC.class, "mdcAdapter", value2);
        assertFalse(value1.getClass().getName().contains("NoopMDCAdapter"));

        MDCAdapter value3 = (MDCAdapter) setStaticFiled(MDC.class, "mdcAdapter", value1);
        assertSame(value2, value3);
    }


    @SneakyThrows
    Object setStaticFiled(Class clazz, String filedName, Object newValue) {
        Field field = clazz.getDeclaredField(filedName);
        field.setAccessible(true);
        Object oldValue = field.get(null);
        field.set(null, newValue);
        return oldValue;
    }

    static class NoopMDCAdapter implements MDCAdapter {

        @Override
        public void put(String key, String val) {

        }

        @Override
        public String get(String key) {
            return "";
        }

        @Override
        public void remove(String key) {

        }

        @Override
        public void clear() {

        }

        @Override
        public Map<String, String> getCopyOfContextMap() {
            return Map.of();
        }

        @Override
        public void setContextMap(Map<String, String> contextMap) {

        }

        //@Override
        public void pushByKey(String key, String value) {

        }

        //@Override
        public String popByKey(String key) {
            return "";
        }

        //@Override
        public Deque<String> getCopyOfDequeByKey(String key) {
            return null;
        }

        //@Override
        public void clearDequeByKey(String key) {

        }
    }
}
