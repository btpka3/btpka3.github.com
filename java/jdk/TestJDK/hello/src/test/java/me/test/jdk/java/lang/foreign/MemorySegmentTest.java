package me.test.jdk.java.lang.foreign;

import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2025/5/7
 * @see java.lang.foreign.MemorySegment
 */
public class MemorySegmentTest {
    @Test
    public void x() {
        //        MemorySegment tMS = null;
        //        ByteBuffer bf = null;
        //        MemorySegment gMS = Arena.global().allocate(100);
        //        try (Arena ar = Arena.ofConfined()) {
        //            tMS = ar.allocate(100);
        //            bf = tMS.asByteBuffer();//一个背靠tMS的视图Buffer
        //            /*
        //            本代码块范围内就是arena的生命周期边界,并且其他线程无法访问tMS。
        //            生成的视图ByteBuffer可以兼容所有旧代码，使用起来非常方便。
        //            */
        //
        //            tMS.get(ValueLayout.JAVA_BYTE, 0);
        //            bf.get(12);
        //
        //        } // segment region deallocated here
        //        tMS.get(ValueLayout.JAVA_BYTE, 0); // 抛出异常，tMS已经不可用。
        //        bf.get(12);//抛出异常，其背后的MS已经不可用
        //        gMS.get(ValueLayout.JAVA_BYTE, 0);// 全局可用可，多线程可接入
    }
}
