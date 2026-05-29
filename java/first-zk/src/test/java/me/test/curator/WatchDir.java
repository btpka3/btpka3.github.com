package me.test.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;

/**
 * CuratorCache 【默认】只监听 直接子级的变化，
 * 即 如果监听 "/survival/${appName}", 则只能监听到 "/survival/${appName}/${cluster}" 的变化，
 * 感知不到 "/survival/${appName}/${cluster}/${ip}" 的变化。
 *
 * @author dangqian.zll
 * @date 2026/5/9
 */
public class WatchDir {
    CuratorFramework curator = null;
    /**
     * 示例 zookeeper path : /survival/${appName}/${cluster}/${ip}
     */
    String parentPath = "/survival/appName1/cluster1";
    // 创建 CuratorCache（替代废弃的 PathChildrenCache）
    CuratorCache cache = CuratorCache.builder(curator, parentPath).build();

    public void init() {
        watchOneLevelDir();
        watchAllTree();
        curator.start();
    }

    public void watchOneLevelDir() {
        // 注册监听器
        CuratorCacheListener listener = CuratorCacheListener.builder()
                .forCreates(childData -> handleNodeCreate(childData))
                .forDeletes(childData -> handleNodeDelete(childData))
                .forChanges((oldData, newData) -> handleNodeChange(oldData, newData))
                .forInitialized(() -> handleInitialized())
                .build();

        cache.listenable().addListener(listener);
    }

    public void watchAllTree() {
        CuratorCacheListener listener = CuratorCacheListener.builder()
                .forCreates(childData -> {
                    String path = childData.getPath();
                    // 通过路径深度区分节点类型
                    String[] segments = path.split("/");
                    int depth = segments.length - 1; // 去掉空的首段

                    if (depth == 3) {
                        // /survival/${appName}/${cluster} → 新增 cluster
                        String cluster = segments[3];
                        System.out.println("[新增cluster] " + cluster);
                    } else if (depth == 4) {
                        // /survival/${appName}/${cluster}/${ip} → 新增 IP
                        String cluster = segments[3];
                        String ip = segments[4];
                        System.out.println("[新增IP] cluster=" + cluster + ", ip=" + ip);
                    }
                })
                .forDeletes(childData -> {
                    // 注意：删除事件的 childData 可能只包含路径，数据为 null
                    String path = childData.getPath();
                    String[] segments = path.split("/");
                    int depth = segments.length - 1;

                    if (depth == 4) {
                        String cluster = segments[3];
                        String ip = segments[4];
                        System.out.println("[IP断联] cluster=" + cluster + ", ip=" + ip);
                    }
                })
                .build();

        cache.listenable().addListener(listener);
    }

    private void handleNodeCreate(org.apache.curator.framework.recipes.cache.ChildData data) {
        String path = data.getPath();
        String nodeName = path.substring(path.lastIndexOf('/') + 1);
        System.out.println("[新增] 子节点: " + nodeName);
        printCurrentCount();
    }

    private void handleNodeDelete(org.apache.curator.framework.recipes.cache.ChildData data) {
        String path = data.getPath();
        String nodeName = path.substring(path.lastIndexOf('/') + 1);
        System.out.println("[删除] 子节点: " + nodeName);
        printCurrentCount();
    }

    private void handleNodeChange(
            org.apache.curator.framework.recipes.cache.ChildData oldData,
            org.apache.curator.framework.recipes.cache.ChildData newData) {
        // 子节点数据变更（你的场景是 IP 节点断联删除，这个可能用不到）
        System.out.println("[变更] 子节点: " + newData.getPath());
    }

    private void handleInitialized() {
        System.out.println("[初始化] 缓存加载完成");
        printCurrentCount();
    }

    /**
     * 获取当前子节点数量
     */
    public int getCurrentCount() {
        return (int) cache.stream().count();
    }

    private void printCurrentCount() {
        System.out.println("当前存活节点数: " + getCurrentCount());
    }
}
