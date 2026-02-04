package me.test.jdk.java.lang;

import org.apache.commons.lang3.tuple.MutablePair;

/**
 * @author dangqian.zll
 * @date 2025/7/17
 */
public class ClassLoader5Runnable implements Runnable {
    public static final ThreadLocal HOLDER = new ThreadLocal();

    @Override
    public void run() {
        Object o = ClassLoader5TcHolder.HOLDER.get();
        if (o == null) {
            System.out.println("======== o = null");
            return;
        }

        // ClassLoader preTccl = Thread.currentThread().getContextClassLoader();
        try {
            System.out.println("======== o is not null");
            System.out.println("======== o.getClass() = "
                    + o.getClass().getProtectionDomain().getCodeSource().getLocation());
            System.out.println("======== o.getClass().getClassLoader() = "
                    + o.getClass().getClassLoader().hashCode());

            // 设置TCCL 不管用，仍然是从当前类：ClassLoader5Test 的classloader 加载，并 ClassCastException
            // Thread.currentThread().setContextClassLoader(o.getClass().getClassLoader());
            MutablePair p = (MutablePair) o;
            System.out.println("======== o = " + p);

        } catch (Throwable e) {
            System.out.println("======== err : " + e.getMessage());
            throw e;
        } finally {
            // Thread.currentThread().setContextClassLoader(preTccl);
        }
    }
}
