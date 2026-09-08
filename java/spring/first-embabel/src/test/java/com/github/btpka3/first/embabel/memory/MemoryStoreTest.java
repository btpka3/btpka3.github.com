package com.github.btpka3.first.embabel.memory;

import com.github.btpka3.first.embabel.config.FirstEmbabelProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryStoreTest {

    @TempDir
    Path tempDir;

    @Test
    void shouldMatchPatternAndReduceLlmCalls() {
        FirstEmbabelProperties properties = new FirstEmbabelProperties();
        properties.getMemory().setFile(tempDir.resolve("memory.yaml").toString());

        MemoryStore store = new MemoryStore(properties);
        store.load();

        List<ToolCall> plan = List.of(new ToolCall("a1_whoami", Map.of()));
        store.recordSuccess("我是谁", plan, "我是谁");

        IntentToolMapping match = store.findMatch("我是谁");
        assertThat(match).isNotNull();
        assertThat(match.getPattern()).isEqualTo("我是谁");
        assertThat(match.getTools()).hasSize(1);
        assertThat(match.getTools().get(0).name()).isEqualTo("a1_whoami");

        IntentToolMapping noMatch = store.findMatch("天气怎么样");
        assertThat(noMatch).isNull();
    }

    @Test
    void shouldPersistAndReloadMappings() {
        FirstEmbabelProperties properties = new FirstEmbabelProperties();
        properties.getMemory().setFile(tempDir.resolve("memory.yaml").toString());

        MemoryStore store = new MemoryStore(properties);
        store.load();
        store.recordSuccess("部门员工", List.of(new ToolCall("my_dept_members", Map.of())), "部门员工");
        store.save();

        MemoryStore reloaded = new MemoryStore(properties);
        reloaded.load();
        IntentToolMapping match = reloaded.findMatch("我所在部门员工有哪些");
        assertThat(match).isNotNull();
        assertThat(match.getTools().get(0).name()).isEqualTo("my_dept_members");
    }

    @Test
    void shouldUpdateScoreWithGoodAndBad() {
        FirstEmbabelProperties properties = new FirstEmbabelProperties();
        properties.getMemory().setFile(tempDir.resolve("memory.yaml").toString());

        MemoryStore store = new MemoryStore(properties);
        store.load();
        store.recordSuccess("test", List.of(new ToolCall("x", Map.of())), "test");

        store.updateScore("test", 0.5);
        assertThat(store.findMatch("test").getSuccessScore()).isEqualTo(1.0);

        store.updateScore("test", -2.0);
        assertThat(store.findMatch("test").getSuccessScore()).isEqualTo(-1.0);
    }
}
