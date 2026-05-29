package me.test.curator;

import lombok.SneakyThrows;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.Stat;

/**
 *
 * @author dangqian.zll
 * @date 2026/5/11
 */
public class SetDataWIthVersionTest {

    @SneakyThrows
    public void x() {
        // 带版本号的 CAS 更新，避免并发覆盖
        String path = null;
        Stat stat = new Stat();
        CuratorFramework client = null;
        client.getData().storingStatIn(stat).forPath(path);
        int version = stat.getVersion();
        byte[] newData = null;
        client.setData().withVersion(version).forPath(path, newData);
    }
}
