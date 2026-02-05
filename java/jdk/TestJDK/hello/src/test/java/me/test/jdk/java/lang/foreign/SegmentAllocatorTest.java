package me.test.jdk.java.lang.foreign;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.ValueLayout;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * SegmentAllocator 示例：展示内存分配的不同方式
 * 
 * @author dangqian.zll
 * @date 2025/5/7
 * @see java.lang.foreign.SegmentAllocator
 */
@Slf4j
public class SegmentAllocatorTest {

    /**
     * 示例：使用 Arena 作为分配器
     */
    @Test
    public void arenaAsAllocator() {
        try (Arena arena = Arena.ofConfined()) {
            // Arena 本身就是 SegmentAllocator
            SegmentAllocator allocator = arena;
            
            // 分配不同类型的数据
            MemorySegment byteSeg = allocator.allocate(ValueLayout.JAVA_BYTE);
            MemorySegment intSeg = allocator.allocate(ValueLayout.JAVA_INT);
            MemorySegment longSeg = allocator.allocate(ValueLayout.JAVA_LONG);
            MemorySegment doubleSeg = allocator.allocate(ValueLayout.JAVA_DOUBLE);
            
            // 写入数据
            byteSeg.set(ValueLayout.JAVA_BYTE, 0, (byte) 127);
            intSeg.set(ValueLayout.JAVA_INT, 0, 42);
            longSeg.set(ValueLayout.JAVA_LONG, 0, 9876543210L);
            doubleSeg.set(ValueLayout.JAVA_DOUBLE, 0, 3.1415926);
            
            // 读取并验证
            log.info("✓ byte: {}", byteSeg.get(ValueLayout.JAVA_BYTE, 0));
            log.info("✓ int: {}", intSeg.get(ValueLayout.JAVA_INT, 0));
            log.info("✓ long: {}", longSeg.get(ValueLayout.JAVA_LONG, 0));
            log.info("✓ double: {}", doubleSeg.get(ValueLayout.JAVA_DOUBLE, 0));
            
            log.info("✓ Arena 自动关闭，内存已释放");
        }
    }

    /**
     * 示例：分配数组
     */
    @Test
    public void allocateArray() {
        try (Arena arena = Arena.ofConfined()) {
            // 分配 int 数组
            int intCount = 5;
            MemorySegment intArray = arena.allocate(ValueLayout.JAVA_INT, intCount);
            
            log.info("✓ int 数组:");
            for (int i = 0; i < intCount; i++) {
                intArray.setAtIndex(ValueLayout.JAVA_INT, i, i * 10);
                log.info("  [{}] = {}", i, intArray.getAtIndex(ValueLayout.JAVA_INT, i));
            }
            
            // 分配 double 数组
            int doubleCount = 3;
            MemorySegment doubleArray = arena.allocate(ValueLayout.JAVA_DOUBLE, doubleCount);
            
            log.info("✓ double 数组:");
            for (int i = 0; i < doubleCount; i++) {
                doubleArray.setAtIndex(ValueLayout.JAVA_DOUBLE, i, 1.5 * i);
                log.info("  [{}] = {}", i, doubleArray.getAtIndex(ValueLayout.JAVA_DOUBLE, i));
            }
        }
    }

    /**
     * 示例：使用 Slice Allocator（在预分配的段上进行多次小分配）
     */
    @Test
    public void sliceAllocator() {
        try (Arena arena = Arena.ofConfined()) {
            // 预分配一个大段
            MemorySegment bigSegment = arena.allocate(1024);
            
            // 创建 slice allocator（作用域内连续分配，无需单独释放）
            SegmentAllocator sliceAllocator = SegmentAllocator.slicingAllocator(bigSegment);
            
            // 进行多次小分配
            MemorySegment seg1 = sliceAllocator.allocate(ValueLayout.JAVA_INT);
            MemorySegment seg2 = sliceAllocator.allocate(ValueLayout.JAVA_LONG);
            MemorySegment seg3 = sliceAllocator.allocate(ValueLayout.JAVA_DOUBLE);
            
            // 写入数据
            seg1.set(ValueLayout.JAVA_INT, 0, 100);
            seg2.set(ValueLayout.JAVA_LONG, 0, 200L);
            seg3.set(ValueLayout.JAVA_DOUBLE, 0, 300.5);
            
            log.info("✓ Slice allocator 分配的段1: {}", seg1.get(ValueLayout.JAVA_INT, 0));
            log.info("✓ Slice allocator 分配的段2: {}", seg2.get(ValueLayout.JAVA_LONG, 0));
            log.info("✓ Slice allocator 分配的段3: {}", seg3.get(ValueLayout.JAVA_DOUBLE, 0));
            
            log.info("✓ 原始段大小: {}", bigSegment.byteSize());
            log.info("✓ 已使用: {}", seg1.byteSize() + seg2.byteSize() + seg3.byteSize());
        }
    }

    /**
     * 示例：与 NIO Buffer 互操作
     */
    @Test
    public void nioBufferInterop() {
        try (Arena arena = Arena.ofConfined()) {
            // 分配内存并转换为各种 Buffer
            MemorySegment segment = arena.allocate(ValueLayout.JAVA_DOUBLE, 4);
            
            // 转换为 ByteBuffer
            ByteBuffer byteBuffer = segment.asByteBuffer();
            byteBuffer.order(ByteOrder.nativeOrder());
            
            // 转换为 DoubleBuffer
            DoubleBuffer doubleBuffer = byteBuffer.asDoubleBuffer();
            
            // 使用 Buffer 写入数据
            for (int i = 0; i < 4; i++) {
                doubleBuffer.put(i, Math.PI * (i + 1));
            }
            
            // 从 Buffer 读取
            log.info("✓ 通过 DoubleBuffer 读取:");
            for (int i = 0; i < 4; i++) {
                log.info("  [{}] = {}", i, doubleBuffer.get(i));
            }
            
            // 直接从 MemorySegment 读取（数据共享）
            log.info("✓ 通过 MemorySegment 读取:");
            for (int i = 0; i < 4; i++) {
                log.info("  [{}] = {}", i, segment.getAtIndex(ValueLayout.JAVA_DOUBLE, i));
            }
        }
    }

    /**
     * 示例：结构体分配（模拟 C 结构体）
     */
    @Test
    public void structAllocation() {
        try (Arena arena = Arena.ofConfined()) {
            // 模拟一个 C 结构体：
            // struct Point {
            //     int x;
            //     int y;
            //     double z;
            // };
            
            // 计算结构体大小（可能包含对齐填充）
            long structSize = ValueLayout.JAVA_INT.byteSize() 
                + ValueLayout.JAVA_INT.byteSize()
                + ValueLayout.JAVA_DOUBLE.byteSize();
            
            // 分配结构体内存
            MemorySegment pointStruct = arena.allocate(structSize);
            
            // 手动计算偏移量并写入字段
            long xOffset = 0;
            long yOffset = ValueLayout.JAVA_INT.byteSize();
            long zOffset = yOffset + ValueLayout.JAVA_INT.byteSize();
            
            pointStruct.set(ValueLayout.JAVA_INT, xOffset, 10);
            pointStruct.set(ValueLayout.JAVA_INT, yOffset, 20);
            pointStruct.set(ValueLayout.JAVA_DOUBLE, zOffset, 30.5);
            
            // 读取字段
            int x = pointStruct.get(ValueLayout.JAVA_INT, xOffset);
            int y = pointStruct.get(ValueLayout.JAVA_INT, yOffset);
            double z = pointStruct.get(ValueLayout.JAVA_DOUBLE, zOffset);
            
            log.info("✓ Point 结构体:");
            log.info("  x = {}", x);
            log.info("  y = {}", y);
            log.info("  z = {}", z);
        }
    }

    /**
     * 示例：使用 Prefix Allocator（重用已释放的段）
     */
    @Test
    public void prefixAllocator() {
        try (Arena arena = Arena.ofConfined()) {
            // 预分配内存段
            MemorySegment segment = arena.allocate(128);
            
            // 创建 prefix allocator（自动回收并重用已分配的段）
            SegmentAllocator prefixAllocator = SegmentAllocator.prefixAllocator(segment);
            
            // 第一次分配
            MemorySegment alloc1 = prefixAllocator.allocate(32);
            log.info("✓ 分配1: 偏移量 = {}, 大小 = {}", alloc1.address(), alloc1.byteSize());
            
            // 第二次分配（会紧接在第一次之后）
            MemorySegment alloc2 = prefixAllocator.allocate(32);
            log.info("✓ 分配2: 偏移量 = {}, 大小 = {}", alloc2.address(), alloc2.byteSize());
            
            // 第三次分配
            MemorySegment alloc3 = prefixAllocator.allocate(32);
            log.info("✓ 分配3: 偏移量 = {}, 大小 = {}", alloc3.address(), alloc3.byteSize());
            
            // 检查总使用量
            log.info("✓ 总分配大小: {}", alloc1.byteSize() + alloc2.byteSize() + alloc3.byteSize());
        }
    }
}