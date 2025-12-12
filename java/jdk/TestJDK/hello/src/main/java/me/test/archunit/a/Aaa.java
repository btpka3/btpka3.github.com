package me.test.archunit.a;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author dangqian.zll
 * @date 2025/12/12
 */
public class Aaa {
    public void x() {
        Executor e = new ThreadPoolExecutor(2, 10, 1, TimeUnit.MINUTES, new ArrayBlockingQueue(60));
        System.out.println(e);
    }
}
