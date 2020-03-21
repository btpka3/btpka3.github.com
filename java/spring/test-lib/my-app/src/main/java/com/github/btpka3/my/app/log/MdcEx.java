package com.github.btpka3.my.app.log;

import org.slf4j.MDC;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * @author dangqian.zll
 * @date 2020-03-21
 */
public class MdcEx {

    protected static final Object NULL_PLACEHOLDER = new Object();
    protected static ThreadLocal<Map<String, Stack<Object>>> l = ThreadLocal.withInitial(() -> new ConcurrentHashMap<>(16));

    public static void run(
            @Nonnull String key,
            @Nullable Object value,
            @Nonnull Runnable runnable
    ) {
        Map<String, Stack<Object>> m = l.get();

        Stack<Object> stack = m.computeIfAbsent(key, k -> new Stack<>());
        Object valueObj = value != null ? value : NULL_PLACEHOLDER;
        try {
            stack.push(valueObj);

            String valueStr = getValueString(valueObj);
            MDC.put(key, valueStr);

            runnable.run();
        } finally {

            if (!stack.isEmpty()) {
                stack.pop();
                if (stack.isEmpty()) {
                    MDC.remove(key);
                } else {
                    Object preValueObj = stack.peek();
                    String preValueStr = getValueString(preValueObj);
                    MDC.put(key, preValueStr);
                }
            } else {
                MDC.remove(key);
            }
        }
    }

    protected static String getValueString(Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof Supplier) {
            return getValueString(((Supplier<?>) o).get());
        }
        return o.toString();
    }

    public static <T> T call(
            @Nonnull String key,
            @Nullable Object s,
            @Nonnull Supplier<T> supplier
    ) {
        Map<String, Stack<Object>> m = l.get();
        Stack<Object> stack = m.computeIfAbsent(key, k -> new Stack<>());
        Object v = s != null ? s : NULL_PLACEHOLDER;
        try {
            stack.push(v);

            return supplier.get();
        } finally {
            stack.pop();
        }
    }

    public static Runnable toRunnable(
            @Nonnull String key,
            @Nullable Object value,
            @Nonnull Runnable runnable
    ) {
        return () -> run(key, value, runnable);
    }

    public static <T> Supplier<T> toSupplier(
            @Nonnull String key,
            @Nullable Object s,
            @Nonnull Supplier<T> supplier
    ) {
        return () -> call(key, s, supplier);
    }

    public static Xxx withMdc(
            @Nonnull String key,
            @Nullable Object value
    ) {
        List<Xxx.Pair<String, Object>> list = new ArrayList<>(1);
        list.add(new Xxx.Pair<>(key, value));
        return new Xxx(list);
    }


    public static class Xxx {


        private static class Pair<K, V> {
            private K key;
            private V value;

            public Pair(K key, V value) {
                this.key = key;
                this.value = value;
            }

            public K getKey() {
                return key;
            }

            public void setKey(K key) {
                this.key = key;
            }

            public V getValue() {
                return value;
            }

            public void setValue(V value) {
                this.value = value;
            }
        }

        private List<Pair<String, Object>> list = Collections.emptyList();

        public Xxx(List<Pair<String, Object>> list) {
            this.list = list;
        }

        public Xxx withMdc(
                @Nonnull String key,
                @Nullable Object value
        ) {
            List<Pair<String, Object>> list = new ArrayList<>(this.list.size() + 1);
            list.addAll(this.list);
            list.add(new Pair<>(key, value));
            return new Xxx(list);
        }

        public void run(
                @Nonnull Runnable runnable
        ) {
            if (this.list.isEmpty()) {
                runnable.run();
                return;
            }

            Runnable last = runnable;
            for (int i = this.list.size() - 1; i >= 0; i--) {
                Pair<String, Object> p = this.list.get(i);
                last = MdcEx.toRunnable(p.getKey(), p.getValue(), last);
            }
            last.run();
        }


    }

}
