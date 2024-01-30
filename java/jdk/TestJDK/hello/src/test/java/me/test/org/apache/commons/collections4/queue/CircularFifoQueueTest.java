package me.test.org.apache.commons.collections4.queue;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/1/30
 */
public class CircularFifoQueueTest {

    @Test
    public void test() {
        // 先进先出 只保留最后几个。
        CircularFifoQueue<String> queue = new CircularFifoQueue<>(3);
        queue.add("aaa");
        queue.add("bbb");
        queue.add("ccc");
        queue.add("eee");
        System.out.println(queue);
    }
}
