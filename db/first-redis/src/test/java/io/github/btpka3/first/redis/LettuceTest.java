package io.github.btpka3.first.redis;

import io.lettuce.core.LettuceFutures;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisStringAsyncCommands;
import io.lettuce.core.api.reactive.RedisStringReactiveCommands;
import io.lettuce.core.api.sync.RedisStringCommands;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author dangqian.zll
 * @date 2023/6/19
 * @see <a href="https://github.com/lettuce-io/lettuce-core">lettuce</a>
 */
public class LettuceTest {


    @Test
    public void sync01() {
        RedisClient client = RedisClient.create("redis://localhost");
        try {
            StatefulRedisConnection<String, String> connection = client.connect();
            RedisStringCommands sync = connection.sync();
            {
                System.out.println("sync:1: foo = " + sync.get("foo"));
                sync.set("foo", "foo-lettuce-sync-" + System.currentTimeMillis());
                System.out.println("sync:2: foo = " + sync.get("foo"));
            }
        } finally {
            client.shutdown();
        }
    }


    @SneakyThrows
    @Test
    public void async01() {
        RedisClient client = RedisClient.create("redis://localhost");
        StatefulRedisConnection<String, String> connection = client.connect();
        try {
            RedisStringAsyncCommands<String, String> async = connection.async();
            {
                {
                    RedisFuture<String> get = async.get("foo");
                    LettuceFutures.awaitAll(1, TimeUnit.SECONDS, get);
                    System.out.println("async:1: foo = " + get.get());
                }
                {
                    RedisFuture<String> set = async.set("foo", "foo-lettuce-async-" + System.currentTimeMillis());
                    LettuceFutures.awaitAll(1, TimeUnit.SECONDS, set);
                }
                {
                    RedisFuture<String> get = async.get("foo");
                    LettuceFutures.awaitAll(1, TimeUnit.SECONDS, get);
                    System.out.println("async:2: foo = " + get.get());
                }
            }
        } finally {
            client.shutdown();
        }
    }

    @Test
    public void reactive01() {
        RedisClient client = RedisClient.create("redis://localhost");
        StatefulRedisConnection<String, String> connection = client.connect();
        try {
            RedisStringReactiveCommands<String, String> reactive = connection.reactive();
            {
                System.out.println("reactive:1:  foo = " + reactive.get("foo").block());
                reactive.set("foo", "foo-lettuce-reactive-" + System.currentTimeMillis())
                        .block();
                System.out.println("reactive:2:  foo = " + reactive.get("foo").block());
            }
        } finally {
            client.shutdown();
        }
    }
}
