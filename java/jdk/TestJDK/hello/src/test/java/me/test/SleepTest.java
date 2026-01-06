package me.test;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * @author dangqian.zll
 * @date 2024/1/29
 */
public class SleepTest {

    public static void main(String[] args) throws InterruptedException {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

        long startTime = runtimeMXBean.getStartTime();

        StringUtils.isBlank("123");
        CollectionUtils.isEmpty(null);
        System.out.println("start to sleep, please attach java agent to test, pid=" + runtimeMXBean.getPid());
        Thread.sleep(60 * 60 * 10000);
    }
}
