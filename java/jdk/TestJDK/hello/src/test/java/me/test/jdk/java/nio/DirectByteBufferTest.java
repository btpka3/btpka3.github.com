package me.test.jdk.java.nio;

/**
 * -XX:MaxDirectMemorySize=1g
 *
 * @author dangqian.zll
 * @date 2025/5/7
 * @see jdk.internal.ref.Cleaner
 * @see java.nio.DirectByteBuffer
 * @see io.netty.util.internal.OutOfDirectMemoryError
 */
public class DirectByteBufferTest {

    public void x() {
        // DirectByteBuffer byteBuffer = new DirectByteBuffer(1024);
    }
}
