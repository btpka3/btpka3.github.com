package com.github.btpka3.first.javaagent;

/**
 * @author dangqian.zll
 * @date 2023/8/15
 */
public class SetTccl {

    public static void main(String[] args) {
        Thread curThread = Thread.currentThread();
        final ClassLoader oldTccl = curThread.getContextClassLoader();
        final ClassLoader newTccl = oldTccl != null ? oldTccl : SetTccl.class.getClassLoader();
        curThread.setContextClassLoader(newTccl);

        System.out.println("Done.");
    }
}
