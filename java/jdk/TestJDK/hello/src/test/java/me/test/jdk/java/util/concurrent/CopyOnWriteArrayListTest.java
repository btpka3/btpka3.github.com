package me.test.jdk.java.util.concurrent;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import me.test.U;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 写时copy，能扩大并发，但已经通过迭代器遍历的数据会是老的。
 * 能保证最终一致性。适合读多写少的情形。
 */
public class CopyOnWriteArrayListTest {

    public static void main(String[] args) throws Exception {
        test01();
    }

    public static void test01() throws Exception {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        list.add(100);
        list.add(200);
        list.add(300);
        list.add(400);
        list.add(500);

        Runnable run = () -> {
            Iterator<Integer> it = list.iterator();
            try {
                while (it.hasNext()) {
                    Integer num = it.next();
                    U.print("num", num);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        // 一个消费者并发遍历，但是是老数据，不会出错。
        Thread t1 = new Thread(run);
        t1.setName("AAA");
        t1.start();

        Thread.sleep(1000);

        // 删除前两个元素
        list.remove(0);
        list.remove(0);

        // 再往后添加两个元素
        list.add(600);
        list.add(700);

        // 另外一个一个消费者并发遍历，这次是新数据，不会出错。
        Thread t2 = new Thread(run);
        t2.setName("BBBBBBBBBBBBB");
        t2.start();

    }

    @Test
    public void x() {
        CopyOnWriteArrayList list = new CopyOnWriteArrayList();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        list.remove("aaa");
        Assertions.assertEquals(2, list.size());
    }
}
