package me.test;

import lombok.SneakyThrows;

/**
 * @author dangqian.zll
 * @date 2025/7/29
 */
public class JunitForkTestUtils {

    static final String ENV_KEY = "MY_ENV";
    static final String PROP_KEY = "my_prop";

    @SneakyThrows
    public static void show(String testCase) {

        String envValue = System.getenv(ENV_KEY);
        String propValue = System.getProperty(PROP_KEY);
        System.out.println("======= "
                + "testCase:" + testCase
                + ", PID:" + ProcessHandle.current().pid()
                + ", THREAD_ID:" + Thread.currentThread().threadId()
                + ", THREAD_ID=" + Thread.currentThread().getName()
                + ", ENV[\"" + ENV_KEY + "\"]=" + envValue
                + ", props[\"" + PROP_KEY + "\"]=" + propValue);
        Thread.sleep(1000);
    }
}
