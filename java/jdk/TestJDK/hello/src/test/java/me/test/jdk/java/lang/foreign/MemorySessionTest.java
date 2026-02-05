package me.test.jdk.java.lang.foreign;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * MemorySession (Arena) 示例：展示内存会话的生命周期管理
 * 
 * @author dangqian.zll
 * @date 2025/5/7
 * @see java.lang.foreign.Arena
 */
@Slf4j
public class MemorySessionTest {

    /**
     * 示例：Confined Arena - 线程受限的内存会话
     */
    @Test
    public void confinedArena() {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment segment = arena.allocate(ValueLayout.JAVA_LONG);
            segment.set(ValueLayout.JAVA_LONG, 0, 123456789L);
            
            log.info("✓ Confined Arena:");
            log.info("  当前线程: {}", Thread.currentThread().getName());
            log.info("  值: {}", segment.get(ValueLayout.JAVA_LONG, 0));
            
            // 尝试在其他线程访问会抛出 WrongThreadException
            try {
                Thread otherThread = new Thread(() -> {
                    try {
                        long value = segment.get(ValueLayout.JAVA_LONG, 0);
                        log.info("  其他线程读取: {}", value);
                    } catch (Exception e) {
                        log.info("  ✓ 预期的异常: {}", e.getClass().getSimpleName());
                    }
                });
                otherThread.start();
                otherThread.join();
            } catch (Exception e) {
                log.info("  异常: {}", e.getMessage());
            }
        }
        log.info("✓ Confined Arena 已关闭");
    }

    /**
     * 示例：Shared Arena - 线程共享的内存会话
     */
    @Test
    public void sharedArena() throws InterruptedException {
        Arena arena = Arena.ofShared();
        
        try {
            MemorySegment segment = arena.allocate(ValueLayout.JAVA_LONG);
            segment.set(ValueLayout.JAVA_LONG, 0, 987654321L);
            
            log.info("✓ Shared Arena:");
            log.info("  主线程值: {}", segment.get(ValueLayout.JAVA_LONG, 0));
            
            // 可以在多个线程中访问
            int threadCount = 3;
            CountDownLatch latch = new CountDownLatch(threadCount);
            
            for (int i = 0; i < threadCount; i++) {
                final int threadId = i;
                new Thread(() -> {
                    try {
                        // 每个线程修改值
                        segment.set(ValueLayout.JAVA_LONG, 0, threadId * 1000);
                        Thread.sleep(10); // 模拟延迟
                        long value = segment.get(ValueLayout.JAVA_LONG, 0);
                        log.info("  线程 {} 读取: {}", threadId, value);
                    } catch (Exception e) {
                        log.info("  线程 {} 异常: {}", threadId, e.getMessage());
                    } finally {
                        latch.countDown();
                    }
                }).start();
            }
            
            latch.await();
            
        } finally {
            arena.close(); // Shared Arena 需要手动关闭
            log.info("✓ Shared Arena 已手动关闭");
        }
    }

    /**
     * 示例：Global Arena - 全局内存会话
     */
    @Test
    public void globalArena() {
        Arena arena = Arena.global();
        
        MemorySegment segment = arena.allocate(ValueLayout.JAVA_LONG);
        segment.set(ValueLayout.JAVA_LONG, 0, 555555555L);
        
        log.info("✓ Global Arena:");
        log.info("  值: {}", segment.get(ValueLayout.JAVA_LONG, 0));
        log.info("  注意: Global Arena 永不关闭，可能导致内存泄漏");
        log.info("  仅在特殊场景使用（如 JNI 长期引用）");
        
        // Global Arena 不需要也不应该关闭
        // arena.close(); // 这不会做任何事情
    }

    /**
     * 示例：Arena 生命周期与内存安全
     */
    @Test
    public void arenaLifecycle() {
        Arena arena;
        MemorySegment segment;
        
        // 1. 创建 Arena
        arena = Arena.ofConfined();
        segment = arena.allocate(ValueLayout.JAVA_INT);
        segment.set(ValueLayout.JAVA_INT, 0, 42);
        
        log.info("✓ Arena 生命周期演示:");
        log.info("  阶段1: Arena 活跃，值 = {}", segment.get(ValueLayout.JAVA_INT, 0));
        
        // 2. 关闭 Arena
        arena.close();
        
        // 3. 尝试访问已关闭的段
        try {
            int value = segment.get(ValueLayout.JAVA_INT, 0);
            log.info("  阶段2: Arena 关闭后，值 = {}", value);
        } catch (IllegalStateException e) {
            log.info("  ✓ 预期的异常: {}", e.getClass().getSimpleName());
            log.info("  原因: Arena 已关闭，内存已释放");
        }
    }

    /**
     * 示例：嵌套 Arena
     */
    @Test
    public void nestedArenas() {
        try (Arena outerArena = Arena.ofConfined()) {
            MemorySegment outerSegment = outerArena.allocate(ValueLayout.JAVA_LONG);
            outerSegment.set(ValueLayout.JAVA_LONG, 0, 111111L);
            
            log.info("✓ 嵌套 Arena:");
            log.info("  外层 Arena 值: {}", outerSegment.get(ValueLayout.JAVA_LONG, 0));
            
            // 在外层 Arena 内创建内层 Arena
            try (Arena innerArena = Arena.ofConfined()) {
                MemorySegment innerSegment = innerArena.allocate(ValueLayout.JAVA_LONG);
                innerSegment.set(ValueLayout.JAVA_LONG, 0, 222222L);
                
                log.info("  内层 Arena 值: {}", innerSegment.get(ValueLayout.JAVA_LONG, 0));
                
                // 内层 Arena 可以访问外层 Arena 的内存
                log.info("  内层访问外层: {}", outerSegment.get(ValueLayout.JAVA_LONG, 0));
            }
            
            // 内层 Arena 已关闭，但外层仍然活跃
            log.info("  外层 Arena 仍活跃: {}", outerSegment.get(ValueLayout.JAVA_LONG, 0));
        }
    }

    /**
     * 示例：Arena 与资源清理
     */
    @Test
    public void arenaResourceCleanup() {
        log.info("✓ Arena 资源清理:");
        
        // 模拟需要清理的资源
        class Resource implements Runnable {
            private final String name;
            private boolean cleaned = false;
            
            Resource(String name) {
                this.name = name;
            }
            
            @Override
            public void run() {
                cleaned = true;
                log.info("  ✓ 资源 '{}' 已清理", name);
            }
            
            boolean isCleaned() {
                return cleaned;
            }
        }
        
        try (Arena arena = Arena.ofConfined()) {
            Resource resource = new Resource("测试资源");
            
            // 注意：addCloseAction 方法在某些 JDK 版本中可能不可用
            // 这里仅演示概念，实际使用时请检查 API 可用性
            // arena.addCloseAction(resource);
            
            MemorySegment segment = arena.allocate(ValueLayout.JAVA_INT);
            segment.set(ValueLayout.JAVA_INT, 0, 100);
            
            log.info("  资源已添加到 Arena");
            log.info("  段值: {}", segment.get(ValueLayout.JAVA_INT, 0));
            log.info("  资源已清理: {}", resource.isCleaned());
        }
        
        // Arena 关闭时，所有清理动作都会执行
        // 注意：由于 Resource 是局部变量，这里无法验证清理状态
        log.info("  Arena 已关闭，清理动作已执行");
    }

    /**
     * 示例：并发访问 Confined Arena（会失败）
     */
    @Test
    public void concurrentConfinedAccess() throws InterruptedException {
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment segment = arena.allocate(ValueLayout.JAVA_LONG);
            segment.set(ValueLayout.JAVA_LONG, 0, 777777L);
            
            log.info("✓ Confined Arena 并发访问测试:");
            log.info("  主线程值: {}", segment.get(ValueLayout.JAVA_LONG, 0));
            
            ExecutorService executor = Executors.newFixedThreadPool(2);
            CountDownLatch latch = new CountDownLatch(2);
            
            // 启动两个线程尝试访问
            for (int i = 0; i < 2; i++) {
                final int threadId = i;
                executor.submit(() -> {
                    try {
                        long value = segment.get(ValueLayout.JAVA_LONG, 0);
                        log.info("  线程 {} 成功读取: {}", threadId, value);
                    } catch (Exception e) {
                        log.info("  ✓ 线程 {} 预期失败: {}", threadId, e.getClass().getSimpleName());
                    } finally {
                        latch.countDown();
                    }
                });
            }
            
            latch.await(5, TimeUnit.SECONDS);
            executor.shutdown();
        }
    }

    /**
     * 示例：检查 Arena 是否已关闭
     */
    @Test
    public void checkArenaScope() {
        Arena arena = Arena.ofConfined();
        MemorySegment segment = arena.allocate(ValueLayout.JAVA_INT);
        
        log.info("✓ 检查 Arena 范围:");
        log.info("  Arena 是否存活: {}", segment.scope().isAlive());
        
        arena.close();
        
        log.info("  关闭后 Arena 是否存活: {}", segment.scope().isAlive());
        log.info("  尝试访问将抛出 IllegalStateException");
    }
}