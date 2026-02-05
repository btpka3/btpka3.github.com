package me.test.jdk.java.lang.foreign;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2025/5/7
 * @see java.lang.foreign.Arena
 * @see java.lang.foreign.MemorySegment
 * @see java.lang.foreign.ValueLayout
 */
public class ArenaTest {


    /**
     * • Arena.ofConfined()：推荐，线程局部、高性能，配合 try-with-resources. 有线程检查：跨线程访问抛 WrongThreadException
     * • Arena.ofShared()：跨线程安全，需手动 close()
     * • Arena.global()：永不解绑（⚠️ 易泄漏，仅特殊场景）
     */
    @Test
    public void foreignMemoryAllocation02() {
        // ✅ 推荐：使用 try-with-resources 确保 Arena 自动关闭（内存释放）
        try (Arena arena = Arena.ofConfined()) {

            // ===== 示例1：分配单个 long (8字节) =====
            {
                MemorySegment longSeg = arena.allocate(ValueLayout.JAVA_LONG);
                longSeg.set(ValueLayout.JAVA_LONG, 0, 9876543210L);
                System.out.println("✓ 单个 long 值: " + longSeg.get(ValueLayout.JAVA_LONG, 0));
            }
            {
                // ===== 示例2：分配 5 个 int 的数组 (20字节) =====
                int size = 5;
                MemorySegment intArray = arena.allocate(ValueLayout.JAVA_INT, size); // 推荐：语义清晰
                // 写入数据（使用索引方法更安全）
                for (int i = 0; i < size; i++) {
                    intArray.setAtIndex(ValueLayout.JAVA_INT, i, (i + 1) * 100);
                }

                // 读取验证
                System.out.print("✓ int 数组内容: [");
                for (int i = 0; i < size; i++) {
                    System.out.print(intArray.getAtIndex(ValueLayout.JAVA_INT, i));
                    if (i < size - 1) System.out.print(", ");
                }
                System.out.println("]");
            }
        }
        System.out.println("✓ Arena 已自动关闭，外部内存已安全释放");

    }
}
