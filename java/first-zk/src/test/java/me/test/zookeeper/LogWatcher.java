package me.test.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import static org.apache.zookeeper.Watcher.Event.KeeperState.Expired;

/**
 *
 * @author dangqian.zll
 * @date 2025/9/19
 */
@Slf4j
public class LogWatcher implements Watcher {
    @Override
    public void process(WatchedEvent watchedEvent) {
        log.info("========== ME:{}", watchedEvent);
        if (Expired == watchedEvent.getState()) {
            log.info("========== ME: session expired, TODO: should restart");
            // TODO 重启
        }

    }
}
