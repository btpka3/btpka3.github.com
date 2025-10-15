package me.test.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 *
 * @author dangqian.zll
 * @date 2025/9/19
 */
public class LogWatcher implements Watcher {
    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("========== ME:" + watchedEvent.toString());
    }
}
