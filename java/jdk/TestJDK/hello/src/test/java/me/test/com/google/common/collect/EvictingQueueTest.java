package me.test.com.google.common.collect;

import com.google.common.collect.EvictingQueue;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/1/30
 */
public class EvictingQueueTest {

    @Test
    public void test() {
        // 先进先出
        EvictingQueue<String> queue = EvictingQueue.create(3);
        queue.add("ccc");
        queue.add("eee");
        queue.add("aaa");
        queue.add("ddd");
        queue.add("bbb");
        System.out.println(queue);
    }
}
