package me.test;

import java.util.ArrayList;
import java.util.List;

import me.test.LockTest.MyTask;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.BeansException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    public static void main(String[] args) throws BeansException {
        final int THREAD_COUNT = 10;
        ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext("applicationContext.xml");
        CuratorFramework client = (CuratorFramework) appCtx.getBean("zkClient");

        List<Thread> list = new ArrayList<Thread>();
        // 创建并启动线程
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread t = new Thread(new MyTask(client, i));
            t.start();
            list.add(t);
        }

        // 等待线程结束
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread t = list.get(i);
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
