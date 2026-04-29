package com.github.btpka3.first.spring.boot3.ai.ali;

import com.alibaba.cloud.ai.graph.CompiledGraph;
import com.alibaba.cloud.ai.graph.KeyStrategy;
import com.alibaba.cloud.ai.graph.KeyStrategyFactory;
import com.alibaba.cloud.ai.graph.StateGraph;
import com.alibaba.cloud.ai.graph.state.strategy.AppendStrategy;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author dangqian.zll
 * @date 2026/4/20
 */
public class StateGraphTest {

    @SneakyThrows
    public void x() {
        StateGraph stateGraph = null;
        // 编译您的图
        CompiledGraph graph = stateGraph.compile();
    }

    public static KeyStrategyFactory createKeyStrategyFactory() {
        return () -> {
            Map<String, KeyStrategy> keyStrategyMap = new HashMap<>();
            keyStrategyMap.put("messages", new AppendStrategy());
            return keyStrategyMap;
        };
    }
}
