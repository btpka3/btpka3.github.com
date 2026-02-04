package me.test.jdk.java.util.concurrent;

import java.util.concurrent.Exchanger;
import me.test.U;

public class ExchangerTest {
    public static void main(String[] args) throws InterruptedException {
        test01();
    }

    public static void test01() throws InterruptedException {
        Exchanger<String[]> exchanger = new Exchanger<>();

        new Thread(() -> {
                    String[] buf = new String[1];

                    for (int i = 0; i < 5; i++) {
                        String msg = "AAA-" + i;
                        buf[0] = msg;
                        try {
                            buf = exchanger.exchange(buf);
                            U.print("生产者", "交换获取了了一个空buf，填充数据 : " + msg);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                })
                .start();

        new Thread(() -> {
                    String[] buf = new String[1];

                    for (int i = 0; i < 5; i++) {
                        try {
                            Thread.sleep(1000);
                            buf = exchanger.exchange(buf);
                            String msg = buf[0];
                            U.print("消费者-------", "交换获取了了一个数据 buf，数据为 : " + msg);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                })
                .start();
    }
}
