package me.test.jdk.java.lang;

/**
 *
 * @author dangqian.zll
 * @date 2026/6/29
 * <p>
 * (1) ThreadLocal 清理：正向：必须在请求结束时调用 remove() 方法，确保 ThreadLocal 为空
 * (2) 标准 API 清理（无反射方案），JDK 至今没有提供"遍历某个线程所有 ThreadLocal 并清空"的公开 API，从 JDK 8 到 JDK 25 都是如此。ThreadLocalMap 是包私有的（java.lang.ThreadLocal.ThreadLocalMap），外部无法访问。
 * @see org.apache.catalina.core.ThreadLocalLeakPreventionListener : 把 Executor 里的所有线程强制销毁重建。线程没了，挂在上面的 ThreadLocalMap 自然也没了。这是 Tomcat 官方推荐的兜底方案，不用写一行反射代码。但我们自己new的线程池则怎么处理？
 * @see ScopedValue
 */
public class ThreadLocalDemo {

    private static final ThreadLocal<String> CTX = new ThreadLocal<>();

    public static void set(String ctx) {
        CTX.set(ctx);
    }

    public static String get() {
        return CTX.get();
    }

    /**
     * 必须在请求结束时调用
     */
    public static void remove() {
        CTX.remove();
    }

    static class MyFilter {
        public void doFilter() {
            try {
                doNext();
            } finally {
                ThreadLocalDemo.remove();  // 关键：请求结束立即清理
            }
        }

        protected void doNext() {

        }
    }
}
