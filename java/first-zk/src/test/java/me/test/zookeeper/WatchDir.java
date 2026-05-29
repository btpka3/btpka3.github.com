package me.test.zookeeper;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;

/**
 *
 * @author dangqian.zll
 * @date 2026/5/9
 */
public class WatchDir {


    public void x() {
        ZooKeeper zk = null;
        String parentPath = "/survival/appName1/cluster1";
        // 监听父节点 /survival/${appName}/${cluster} 的子节点变化
        zk.getChildren(parentPath, event -> {
            if (event.getType() == Watcher.Event.EventType.NodeChildrenChanged) {
                // 子节点被添加、删除时触发
                // 注意：此事件只通知变化发生，不包含具体变化内容
                // 需要重新 getChildren 获取最新列表
                List<String> newChildren = zk.getChildren(parentPath, this); // 重新注册监听
                System.out.println("当前子节点数量: " + newChildren.size());
            }
        });
    }
}
