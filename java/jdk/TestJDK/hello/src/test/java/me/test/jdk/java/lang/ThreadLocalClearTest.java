package me.test.jdk.java.lang;

import java.lang.ref.Reference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Q: tomcat 报错该如何解决？
 * <p>
 * "SEVERE: The web application [] created a ThreadLocal with key of type [java.lang.ThreadLocal.SuppliedThreadLocal] (value [java.lang.ThreadLocal$SuppliedThreadLocal@1039c70b]) and a value of type [com.alibaba.security.gong9.log.api.context.MapBasedTContextImpl] (value [com.alibaba.security.gong9.log.api.context.MapBasedTContextImpl@1b5d4a86]) but failed to remove it when the web application was stopped. Threads are going to be renewed over time to try and avoid a probable memory leak."
 *
 * 不需要处理的场景（大多数生产环境）： 如果每次发布都是重启整个 JVM（kill → start），ThreadLocal 残留会随进程销毁一起释放，不存在真正的泄漏。Tomcat 打这个 SEVERE 日志只是"预防性警告"，并不代表当前已经 OOM。绝大多数 Spring Boot 内嵌 Tomcat 或 Aone 标准发布流程都属于这种情况——完全可以不处理。
 *
 * 需要处理的场景：如果你在同一个 Tomcat 实例上做热部署（undeploy → redeploy，JVM 不重启），每次 redeploy 都会泄漏一份 MapBasedTContextImpl 以及它引用的整个 WebAppClassLoader 及其加载的所有类。反复 redeploy 几十次后，Metaspace/PermGen 会被打满，最终触发 OutOfMemoryError: Metaspace。
 * 热部署时，Tomcat 的工作线程不会被销毁。Tomcat 的线程池（http-nio-*-exec-N）属于 Connector 层，不属于某个 Web 应用。undeploy/redeploy 只是卸载应用的 Context，线程池里的线程依然活着、继续服务其他请求。所以线程上挂的 ThreadLocal entry 也一直存活。
 * 线程不死 → value 不释放 → 旧 ClassLoader 不释放 → 旧 app 的整个类图都留在内存里。
 *
 * @author dangqian.zll
 * @date 2026/6/29
 */
public class ThreadLocalClearTest {

    /*
    核心思路解释一下：

    线程遍历方面，通过 ThreadGroup.enumerate() 从根线程组递归拿到所有活跃线程，包括 Tomcat 的 http-nio-*-exec-N 工作线程。

    ThreadLocalMap 结构方面，每个 Thread 对象内部有一个 threadLocals 字段，类型是 ThreadLocal.ThreadLocalMap，它内部维护了一个 Entry[] table。每个 Entry 是 WeakReference<ThreadLocal<?>> 的子类，额外持有一个强引用的 value 字段——这个 value 就是你看到的 MapBasedTContextImpl 实例。

    清理逻辑：遍历 table，找到 value 类名匹配的 entry，将 value 置 null 并 clear 掉 WeakReference。这样 ThreadLocalMap 在后续操作时会自动清除这些 stale entry，ClassLoader 也就能被回收了。

    JDK 兼容性注意：JDK 17+ 的模块系统默认禁止反射访问 java.lang 内部字段，需要在启动参数加 --add-opens java.base/java.lang=ALL-UNNAMED。JDK 8/11 默认可用。
     */

    public void close() {
        // 策略1: 如果 SDK 提供了清理 API，直接调用
        try {
            Class<?> tContextClass = Class.forName(
                    "com.alibaba.security.gong9.log.api.context.TContext");
            Method removeMethod = tContextClass.getMethod("remove");
            removeMethod.invoke(null);
            System.out.println("[ThreadLocalCleanup] TContext.remove() called on current thread.");
        } catch (Exception e) {
            // SDK 没有提供 remove 方法，走策略2
            System.out.println("[ThreadLocalCleanup] SDK API not available, falling back to reflection.");
        }

        // 策略2: 反射遍历所有线程，清除目标 ThreadLocal
        cleanupThreadLocals("com.alibaba.security.gong9.log.api.context.MapBasedTContextImpl");
    }

    /**
     * 通过反射遍历所有活跃线程的 ThreadLocalMap，
     * 移除 value 类型匹配 targetClassName 的 entry。
     */
    private void cleanupThreadLocals(String targetClassName) {
        // 获取所有活跃线程
        Thread[] threads = getActiveThreads();

        // 反射拿到 Thread 类中的两个 ThreadLocalMap 字段
        Field threadLocalsField = getField(Thread.class, "threadLocals");
        Field inheritableThreadLocalsField = getField(Thread.class, "inheritableThreadLocals");

        if (threadLocalsField == null) {
            System.err.println("[ThreadLocalCleanup] Cannot access Thread.threadLocals field.");
            return;
        }

        int cleaned = 0;
        for (Thread thread : threads) {
            if (thread == null) continue;
            cleaned += cleanMapForThread(thread, threadLocalsField, targetClassName);
            if (inheritableThreadLocalsField != null) {
                cleaned += cleanMapForThread(thread, inheritableThreadLocalsField, targetClassName);
            }
        }
        System.out.println("[ThreadLocalCleanup] Cleaned " + cleaned + " ThreadLocal entries.");
    }

    /**
     * 对单个线程的某个 ThreadLocalMap 字段进行清理
     */
    private int cleanMapForThread(Thread thread, Field mapField, String targetClassName) {
        int count = 0;
        try {
            Object map = mapField.get(thread); // ThreadLocal.ThreadLocalMap 实例
            if (map == null) return 0;

            // ThreadLocalMap 内部是 Entry[] table
            Field tableField = map.getClass().getDeclaredField("table");
            tableField.setAccessible(true);
            Object[] table = (Object[]) tableField.get(map);
            if (table == null) return 0;

            for (Object entry : table) {
                if (entry == null) continue;
                // Entry extends WeakReference<ThreadLocal<?>>，value 是强引用字段
                Field valueField = entry.getClass().getDeclaredField("value");
                valueField.setAccessible(true);
                Object value = valueField.get(entry);

                if (value != null && value.getClass().getName().equals(targetClassName)) {
                    // 清除: 将 value 置 null，并清除 WeakReference 的 referent
                    valueField.set(entry, null);

                    // 同时把 WeakReference 的 referent（即 ThreadLocal key）也清掉
                    // 这样 ThreadLocalMap 在下次 get/set 时会自动 expunge 这个 stale entry
                    if (entry instanceof Reference) {
                        ((Reference<?>) entry).clear();
                    }
                    count++;
                }
            }
        } catch (Exception e) {
            // 不同 JDK 版本内部结构可能有差异，忽略单个线程的异常
            System.err.println("[ThreadLocalCleanup] Error cleaning thread ["
                    + thread.getName() + "]: " + e.getMessage());
        }
        return count;
    }

    /**
     * 获取当前 JVM 中所有活跃线程
     */
    private Thread[] getActiveThreads() {
        // 找到根 ThreadGroup
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        while (group.getParent() != null) {
            group = group.getParent();
        }

        int estimatedSize = group.activeCount() * 2;
        Thread[] threads = new Thread[estimatedSize];
        int actual = group.enumerate(threads, true);

        return Arrays.copyOf(threads, actual);
    }

    /**
     * 反射获取字段并设置 accessible
     */
    private Field getField(Class<?> clazz, String fieldName) {
        try {
            Field f = clazz.getDeclaredField(fieldName);
            f.setAccessible(true);
            return f;
        } catch (NoSuchFieldException e) {
            return null;
        }
    }
}
