package me.test.jdk.java.lang.foreign;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.ByteOrder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * ValueLayout 示例：展示不同数据类型的内存布局
 * 
 * @author dangqian.zll
 * @date 2025/5/7
 * @see java.lang.foreign.ValueLayout
 */
@Slf4j
public class ValueLayoutTest {

    /**
     * 示例：展示所有基本类型的 ValueLayout
     */
    @Test
    public void basicTypeLayouts() {
        log.info("✓ Java 基本类型的 ValueLayout:");
        log.info("  JAVA_BOOLEAN: 大小 = {} 字节", ValueLayout.JAVA_BOOLEAN.byteSize());
        log.info("  JAVA_BYTE: 大小 = {} 字节", ValueLayout.JAVA_BYTE.byteSize());
        log.info("  JAVA_CHAR: 大小 = {} 字节", ValueLayout.JAVA_CHAR.byteSize());
        log.info("  JAVA_SHORT: 大小 = {} 字节", ValueLayout.JAVA_SHORT.byteSize());
        log.info("  JAVA_INT: 大小 = {} 字节", ValueLayout.JAVA_INT.byteSize());
        log.info("  JAVA_LONG: 大小 = {} 字节", ValueLayout.JAVA_LONG.byteSize());
        log.info("  JAVA_FLOAT: 大小 = {} 字节", ValueLayout.JAVA_FLOAT.byteSize());
        log.info("  JAVA_DOUBLE: 大小 = {} 字节", ValueLayout.JAVA_DOUBLE.byteSize());
        log.info("  ADDRESS: 大小 = {} 字节", ValueLayout.ADDRESS.byteSize());
    }

    /**
     * 示例：使用不同字节序写入和读取
     */
    @Test
    public void byteOrder() {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment segment = arena.allocate(ValueLayout.JAVA_LONG);
            
            // 写入一个大端序的值
            segment.set(ValueLayout.JAVA_LONG.withOrder(ByteOrder.BIG_ENDIAN), 0, 0x123456789ABCDEF0L);
            
            // 以小端序读取（结果会不同）
            long littleEndianRead = segment.get(ValueLayout.JAVA_LONG.withOrder(ByteOrder.LITTLE_ENDIAN), 0);
            
            log.info("✓ 原始值 (大端): 0x123456789ABCDEF0");
            log.info("✓ 按小端读取: 0x{}", Long.toHexString(littleEndianRead));
            
            // 以大端序读取（结果相同）
            long bigEndianRead = segment.get(ValueLayout.JAVA_LONG.withOrder(ByteOrder.BIG_ENDIAN), 0);
            
            log.info("✓ 按大端读取: 0x{}", Long.toHexString(bigEndianRead));
            
            log.info("✓ 本机字节序: {}", ByteOrder.nativeOrder());
        }
    }

    /**
     * 示例：使用 C 类型的布局
     */
    @Test
    public void cTypeLayouts() {
        log.info("✓ C 类型的 ValueLayout:");
        log.info("  C_CHAR: 大小 = {} 字节", ValueLayout.JAVA_BYTE.byteSize());
        log.info("  C_SHORT: 大小 = {} 字节", ValueLayout.JAVA_SHORT.byteSize());
        log.info("  C_INT: 大小 = {} 字节", ValueLayout.JAVA_INT.byteSize());
        log.info("  C_LONG: 大小 = {} 字节", ValueLayout.JAVA_LONG.byteSize());
        log.info("  C_LONG_LONG: 大小 = {} 字节", ValueLayout.JAVA_LONG.byteSize());
        log.info("  C_FLOAT: 大小 = {} 字节", ValueLayout.JAVA_FLOAT.byteSize());
        log.info("  C_DOUBLE: 大小 = {} 字节", ValueLayout.JAVA_DOUBLE.byteSize());
        log.info("  C_POINTER (ADDRESS): 大小 = {} 字节", ValueLayout.ADDRESS.byteSize());
    }

    /**
     * 示例：对齐和填充
     */
    @Test
    public void alignmentAndPadding() {
        try (Arena arena = Arena.ofConfined()) {
            // 分配足够大的内存
            MemorySegment segment = arena.allocate(32);
            
            // 检查各类型的对齐要求
            log.info("✓ 各类型的对齐要求（字节）:");
            log.info("  JAVA_BYTE 对齐: {}", ValueLayout.JAVA_BYTE.byteAlignment());
            log.info("  JAVA_SHORT 对齐: {}", ValueLayout.JAVA_SHORT.byteAlignment());
            log.info("  JAVA_INT 对齐: {}", ValueLayout.JAVA_INT.byteAlignment());
            log.info("  JAVA_LONG 对齐: {}", ValueLayout.JAVA_LONG.byteAlignment());
            log.info("  JAVA_DOUBLE 对齐: {}", ValueLayout.JAVA_DOUBLE.byteAlignment());
            
            // 演示按自然对齐写入
            long offset = 0;
            offset += ValueLayout.JAVA_CHAR.byteSize();
            
            // 确保下一个字段正确对齐（按 8 字节对齐）
            offset = (offset + 7) & ~7; // 向上对齐到 8 字节边界
            segment.set(ValueLayout.JAVA_LONG, offset, 123456789L);
            
            log.info("✓ 对齐后的 long 值: {}", segment.get(ValueLayout.JAVA_LONG, offset));
        }
    }

    /**
     * 示例：自定义布局（带名称）
     */
    @Test
    public void customNamedLayouts() {
        try (Arena arena = Arena.ofConfined()) {
            // 创建带名称的自定义布局
            ValueLayout.OfInt ageLayout = ValueLayout.JAVA_INT.withName("age");
            ValueLayout.OfLong idLayout = ValueLayout.JAVA_LONG.withName("id");
            ValueLayout.OfDouble salaryLayout = ValueLayout.JAVA_DOUBLE.withName("salary");
            
            log.info("✓ 自定义布局名称:");
            log.info("  {}", ageLayout.name().orElse("无名称"));
            log.info("  {}", idLayout.name().orElse("无名称"));
            log.info("  {}", salaryLayout.name().orElse("无名称"));
            
            // 使用这些布局
            MemorySegment segment = arena.allocate(
                ageLayout.byteSize() + idLayout.byteSize() + salaryLayout.byteSize()
            );
            
            long offset = 0;
            segment.set(ageLayout, offset, 30);
            offset += ageLayout.byteSize();
            segment.set(idLayout, offset, 1001L);
            offset += idLayout.byteSize();
            segment.set(salaryLayout, offset, 50000.50);
            
            log.info("✓ 使用自定义布局写入的数据:");
            offset = 0;
            log.info("  age: {}", segment.get(ageLayout, offset));
            offset += ageLayout.byteSize();
            log.info("  id: {}", segment.get(idLayout, offset));
            offset += idLayout.byteSize();
            log.info("  salary: {}", segment.get(salaryLayout, offset));
        }
    }

    /**
     * 示例：位操作（通过字节操作模拟）
     */
    @Test
    public void bitOperations() {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment segment = arena.allocate(ValueLayout.JAVA_INT);
            
            // 写入一个整数
            int value = 0b11001100_10101010_11110000_00001111;
            segment.set(ValueLayout.JAVA_INT, 0, value);
            
            log.info("✓ 原始值 (二进制): {}", Integer.toBinaryString(value));
            log.info("✓ 原始值 (十六进制): 0x{}", Integer.toHexString(value));
            
            // 读取字节
            byte byte0 = segment.get(ValueLayout.JAVA_BYTE, 0);
            byte byte1 = segment.get(ValueLayout.JAVA_BYTE, 1);
            byte byte2 = segment.get(ValueLayout.JAVA_BYTE, 2);
            byte byte3 = segment.get(ValueLayout.JAVA_BYTE, 3);
            
            log.info("✓ 各字节值:");
            log.info("  Byte 0: 0x{}", Integer.toHexString(byte0 & 0xFF));
            log.info("  Byte 1: 0x{}", Integer.toHexString(byte1 & 0xFF));
            log.info("  Byte 2: 0x{}", Integer.toHexString(byte2 & 0xFF));
            log.info("  Byte 3: 0x{}", Integer.toHexString(byte3 & 0xFF));
        }
    }
}