package me.test.curator;

import lombok.SneakyThrows;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.SetDataBuilder;
import org.apache.curator.framework.recipes.nodes.PersistentNode;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.TimeUnit;

/**
 * PersistentNode: 一次性创建、自动重建，但不提供更新数据的方法。
 * 如果需要频繁更新，则建议 使用 {@link SetDataBuilder}
 *
 * @author dangqian.zll
 * @date 2026/5/9
 */
public class PersistentNodeTest {

    /// EPHEMERAL 临时节点 的数据在心跳session丢失 后不会自动重建， 但可以通过
    /// PersistentEphemeralNode 来实现
    @SneakyThrows
    public void x() {

        CuratorFramework curatorFramework = null;
        PersistentNode node = new PersistentNode(
                curatorFramework,
                //PersistentEphemeralNode.Mode.EPHEMERAL,
                CreateMode.EPHEMERAL,
                true,
                "/your/heartbeat/path",
                "your-data".getBytes()
        );
        node.start();
        // 等待创建完成
        node.waitForInitialCreate(3, TimeUnit.SECONDS);

        // Curator 会在 LOST -> RECONNECTED 后自动重建该节点
        // 不需要手动处理
        // 需要更新数据时，直接用 Curator 客户端 setData
        byte[] newData = "new-data".getBytes();
        curatorFramework.setData().forPath(node.getActualPath(), newData);
    }
}
