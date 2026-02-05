package me.test.jdk.java.lang.foreign;

import java.lang.foreign.Arena;
import java.lang.foreign.GroupLayout;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SequenceLayout;
import java.lang.foreign.StructLayout;
import java.lang.foreign.UnionLayout;
import java.lang.foreign.ValueLayout;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * GroupLayout 示例：展示如何定义结构体和联合体
 * 
 * @author dangqian.zll
 * @date 2025/5/7
 * @see java.lang.foreign.GroupLayout
 */
@Slf4j
public class GroupLayoutTest {

    /**
     * 示例：定义简单的 C 结构体
     * C 结构体:
     * struct Point {
     *     int x;
     *     int y;
     * };
     */
    @Test
    public void simpleStruct() {
        // 定义结构体布局
        GroupLayout pointLayout = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("x"),
            ValueLayout.JAVA_INT.withName("y")
        );
        
        log.info("✓ 简单结构体 Point:");
        log.info("  布局: {}", pointLayout);
        log.info("  大小: {} 字节", pointLayout.byteSize());
        log.info("  对齐: {} 字节", pointLayout.byteAlignment());
    }

    /**
     * 示例：使用结构体进行内存操作
     */
    @Test
    public void structMemoryOperations() {
        try (Arena arena = Arena.ofConfined()) {
            // 定义结构体
            GroupLayout pointLayout = MemoryLayout.structLayout(
                ValueLayout.JAVA_INT.withName("x"),
                ValueLayout.JAVA_INT.withName("y")
            );
            
            // 分配结构体内存
            MemorySegment pointSegment = arena.allocate(pointLayout);
            
            // 获取成员的偏移量
            long xOffset = pointLayout.byteOffset(MemoryLayout.PathElement.groupElement("x"));
            long yOffset = pointLayout.byteOffset(MemoryLayout.PathElement.groupElement("y"));
            
            // 写入数据
            pointSegment.set(ValueLayout.JAVA_INT, xOffset, 10);
            pointSegment.set(ValueLayout.JAVA_INT, yOffset, 20);
            
            // 读取数据
            int x = pointSegment.get(ValueLayout.JAVA_INT, xOffset);
            int y = pointSegment.get(ValueLayout.JAVA_INT, yOffset);
            
            log.info("✓ 结构体内存操作:");
            log.info("  Point {{ x={}, y={} }}", x, y);
            log.info("  x 偏移量: {}", xOffset);
            log.info("  y 偏移量: {}", yOffset);
        }
    }

    /**
     * 示例：复杂的嵌套结构体
     * C 结构体:
     * struct Rectangle {
     *     struct Point top_left;
     *     struct Point bottom_right;
     *     int color;
     * };
     */
    @Test
    public void nestedStruct() {
        // 定义 Point 结构体
        GroupLayout pointLayout = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("x"),
            ValueLayout.JAVA_INT.withName("y")
        );
        
        // 定义 Rectangle 结构体（嵌套 Point）
        GroupLayout rectangleLayout = MemoryLayout.structLayout(
            pointLayout.withName("top_left"),
            pointLayout.withName("bottom_right"),
            ValueLayout.JAVA_INT.withName("color")
        );
        
        log.info("✓ 嵌套结构体 Rectangle:");
        log.info("  布局: {}", rectangleLayout);
        log.info("  大小: {} 字节", rectangleLayout.byteSize());
        
        // 获取嵌套成员的路径
        var topLeftXPath = MemoryLayout.PathElement.groupElement("top_left");
        var topLeftXElement = MemoryLayout.PathElement.groupElement("x");
        
        long xOffset = rectangleLayout.byteOffset(topLeftXPath, topLeftXElement);
        log.info("  top_left.x 偏移量: {}", xOffset);
    }

    /**
     * 示例：结构体数组
     * C 结构体:
     * struct Point points[3];
     */
    @Test
    public void structArray() {
        try (Arena arena = Arena.ofConfined()) {
            // 定义 Point 结构体
            GroupLayout pointLayout = MemoryLayout.structLayout(
                ValueLayout.JAVA_INT.withName("x"),
                ValueLayout.JAVA_INT.withName("y")
            );
            
            // 定义结构体数组
            SequenceLayout pointsArray = MemoryLayout.sequenceLayout(3, pointLayout);
            
            // 分配数组
            MemorySegment arraySegment = arena.allocate(pointsArray);
            
            // 写入每个 Point
            for (int i = 0; i < 3; i++) {
                long baseOffset = i * pointLayout.byteSize();
                long xOffset = baseOffset + pointLayout.byteOffset(
                    MemoryLayout.PathElement.groupElement("x")
                );
                long yOffset = baseOffset + pointLayout.byteOffset(
                    MemoryLayout.PathElement.groupElement("y")
                );
                
                arraySegment.set(ValueLayout.JAVA_INT, xOffset, i * 10);
                arraySegment.set(ValueLayout.JAVA_INT, yOffset, i * 20);
            }
            
            // 读取并打印
            log.info("✓ 结构体数组:");
            for (int i = 0; i < 3; i++) {
                long baseOffset = i * pointLayout.byteSize();
                long xOffset = baseOffset + pointLayout.byteOffset(
                    MemoryLayout.PathElement.groupElement("x")
                );
                long yOffset = baseOffset + pointLayout.byteOffset(
                    MemoryLayout.PathElement.groupElement("y")
                );
                
                int x = arraySegment.get(ValueLayout.JAVA_INT, xOffset);
                int y = arraySegment.get(ValueLayout.JAVA_INT, yOffset);
                log.info("  points[{}] = {{ x={}, y={} }}", i, x, y);
            }
        }
    }

    /**
     * 示例：联合体（Union）
     * C 联合体:
     * union Data {
     *     int i;
     *     float f;
     *     char str[4];
     * };
     */
    @Test
    public void unionLayout() {
        // 定义联合体
        GroupLayout dataUnion = MemoryLayout.unionLayout(
            ValueLayout.JAVA_INT.withName("i"),
            ValueLayout.JAVA_FLOAT.withName("f"),
            MemoryLayout.sequenceLayout(4, ValueLayout.JAVA_BYTE).withName("str")
        );
        
        log.info("✓ 联合体 Data:");
        log.info("  布局: {}", dataUnion);
        log.info("  大小: {} 字节", dataUnion.byteSize());
        log.info("  注意: 联合体成员共享同一内存空间");
        
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment dataSegment = arena.allocate(dataUnion);
            
            // 写入 int
            dataSegment.set(ValueLayout.JAVA_INT, 0, 0x12345678);
            int intValue = dataSegment.get(ValueLayout.JAVA_INT, 0);
            log.info("  作为 int: 0x{}", Integer.toHexString(intValue));
            
            // 读取为 float（相同的字节，解释为 float）
            float floatValue = dataSegment.get(ValueLayout.JAVA_FLOAT, 0);
            log.info("  作为 float: {}", floatValue);
        }
    }

    /**
     * 示例：带对齐的结构体
     * C 结构体:
     * struct Aligned {
     *     char a;
     *     // 3 字节填充
     *     int b;
     *     short c;
     *     // 2 字节填充
     * };
     */
    @Test
    public void paddedStruct() {
        // 定义带填充的结构体
        GroupLayout alignedLayout = MemoryLayout.structLayout(
            ValueLayout.JAVA_BYTE.withName("a"),
            MemoryLayout.paddingLayout(3),  // 显式填充
            ValueLayout.JAVA_INT.withName("b"),
            ValueLayout.JAVA_SHORT.withName("c"),
            MemoryLayout.paddingLayout(2)   // 显式填充
        );
        
        log.info("✓ 带对齐的结构体:");
        log.info("  布局: {}", alignedLayout);
        log.info("  大小: {} 字节", alignedLayout.byteSize());
        
        // 获取各成员偏移量
        long aOffset = alignedLayout.byteOffset(MemoryLayout.PathElement.groupElement("a"));
        long bOffset = alignedLayout.byteOffset(MemoryLayout.PathElement.groupElement("b"));
        long cOffset = alignedLayout.byteOffset(MemoryLayout.PathElement.groupElement("c"));
        
        log.info("  a 偏移量: {}", aOffset);
        log.info("  b 偏移量: {} (4 字节对齐)", bOffset);
        log.info("  c 偏移量: {}", cOffset);
    }

    /**
     * 示例：结构体包含数组
     * C 结构体:
     * struct Person {
     *     char name[32];
     *     int age;
     *     float scores[5];
     * };
     */
    @Test
    public void structWithArrays() {
        // 定义 Person 结构体
        GroupLayout personLayout = MemoryLayout.structLayout(
            MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE).withName("name"),
            ValueLayout.JAVA_INT.withName("age"),
            MemoryLayout.sequenceLayout(5, ValueLayout.JAVA_FLOAT).withName("scores")
        );
        
        log.info("✓ 包含数组的结构体 Person:");
        log.info("  布局: {}", personLayout);
        log.info("  大小: {} 字节", personLayout.byteSize());
        
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment person = arena.allocate(personLayout);
            
            // 设置 age
            long ageOffset = personLayout.byteOffset(MemoryLayout.PathElement.groupElement("age"));
            person.set(ValueLayout.JAVA_INT, ageOffset, 25);
            
            // 设置 scores
            long scoresOffset = personLayout.byteOffset(MemoryLayout.PathElement.groupElement("scores"));
            for (int i = 0; i < 5; i++) {
                long scoreOffset = scoresOffset + i * ValueLayout.JAVA_FLOAT.byteSize();
                person.set(ValueLayout.JAVA_FLOAT, scoreOffset, 80.0f + i * 2.5f);
            }
            
            // 读取
            int age = person.get(ValueLayout.JAVA_INT, ageOffset);
            log.info("  age: {}", age);
            log.info("  scores:");
            for (int i = 0; i < 5; i++) {
                long scoreOffset = scoresOffset + i * ValueLayout.JAVA_FLOAT.byteSize();
                float score = person.get(ValueLayout.JAVA_FLOAT, scoreOffset);
                log.info("    [{}] = {}", i, score);
            }
        }
    }

    /**
     * 示例：结构体包含指针
     * C 结构体:
     * struct Node {
     *     int value;
     *     struct Node* next;
     * };
     */
    @Test
    public void structWithPointer() {
        // 定义 Node 结构体
        GroupLayout nodeLayout = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("value"),
            ValueLayout.ADDRESS.withName("next")
        );
        
        log.info("✓ 包含指针的结构体 Node:");
        log.info("  布局: {}", nodeLayout);
        log.info("  大小: {} 字节", nodeLayout.byteSize());
        
        try (Arena arena = Arena.ofConfined()) {
            // 创建两个节点
            MemorySegment node1 = arena.allocate(nodeLayout);
            MemorySegment node2 = arena.allocate(nodeLayout);
            
            // 设置值
            long valueOffset1 = nodeLayout.byteOffset(MemoryLayout.PathElement.groupElement("value"));
            long nextOffset1 = nodeLayout.byteOffset(MemoryLayout.PathElement.groupElement("next"));
            
            node1.set(ValueLayout.JAVA_INT, valueOffset1, 10);
            node1.set(ValueLayout.ADDRESS, nextOffset1, node2); // node1 -> node2
            
            node2.set(ValueLayout.JAVA_INT, valueOffset1, 20);
            node2.set(ValueLayout.ADDRESS, nextOffset1, MemorySegment.NULL); // node2 -> NULL
            
            // 读取链表
            log.info("  链表:");
            MemorySegment current = node1;
            int index = 0;
            while (!current.equals(MemorySegment.NULL)) {
                int value = current.get(ValueLayout.JAVA_INT, valueOffset1);
                log.info("    Node {}: value = {}", index, value);
                
                // 获取下一个节点
                current = current.get(ValueLayout.ADDRESS, nextOffset1);
                index++;
                
                if (index > 10) break; // 防止无限循环
            }
        }
    }

    /**
     * 示例：使用 varargs 创建结构体
     */
    @Test
    public void varargsStruct() {
        // 使用 varargs 创建结构体
        GroupLayout varargsLayout = MemoryLayout.structLayout(
            ValueLayout.JAVA_BYTE.withName("a"),
            ValueLayout.JAVA_SHORT.withName("b"),
            ValueLayout.JAVA_INT.withName("c"),
            ValueLayout.JAVA_LONG.withName("d")
        );
        
        log.info("✓ 使用 varargs 创建结构体:");
        log.info("  布局: {}", varargsLayout);
        log.info("  成员数量: {}", varargsLayout.memberLayouts().size());
    }
}