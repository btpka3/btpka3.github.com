package me.test.jdk.java.util.concurrent;

import java.util.concurrent.LinkedTransferQueue;
import me.test.U;

/**
 * BlockingQueue 只会在队列满的时候，生产者将会被阻塞而无法向其添加新消息。
 * <p>
 * 而 TransferQueue 则会让 生产者调用transfer(​E) 方法时，一直阻塞，一直到消费着消费掉消息。
 * <p>
 * 注意：消费者从 queue 中取出消息，就认为是消费了，而消费者后续有没有其他异常，都不管的。
 * <p>
 * 性能测试的结果表明 LinkedTransferQueue 优于Java 5的那些类 (ConcurrentLinkedQueue、SynchronousQueue和LinkedBlockingQueue).
 * LinkedTransferQueue的性能分别是SynchronousQueue的3倍（非公平模式）和14倍（公平模式）。
 * <p>
 * 因为像 ThreadPoolExecutor这样的类 在任务传递时都是使用 SynchronousQueue，
 * 所以使用 LinkedTransferQueue 来代替 SynchronousQueue 也会使得 ThreadPoolExecutor 得到相应的性能提升。
 * <p>
 * SynchronousQueue使用两个队列（一个用于正在等待的生产者、另一个用于正在等待的消费者）和一个用来保护两个队列的锁。
 * 而LinkedTransferQueue使用CAS操作 实现一个非阻塞的方法，这是避免序列化处理任务的关键
 */
public class LinkedTransferQueueTest {

    public static void main(String[] args) throws InterruptedException {
        test01();
    }


    public static void test01() throws InterruptedException {


        LinkedTransferQueue<String> q = new LinkedTransferQueue<>();


        // 生产者
        new Thread(() -> {
            U.print("生产者", "transfer 开始");
            try {
                q.transfer("AAA");
                //q.transfer("BBB");
                U.print("生产者", "transfer 完成");
            } catch (InterruptedException e) {
                U.print("生产者", "transfer 异常");
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(2000);
                U.print("消费者", "take 开始");
                String msg = q.take();
                if ("BBB".equalsIgnoreCase(msg)) {
                    throw new RuntimeException("异常测试");
                }
                U.print("消费者", "take 完成。msg = " + msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

        U.print("主程序", "好戏登场喽~");
    }
}
