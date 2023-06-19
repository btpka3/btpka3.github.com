package io.github.btpka3.first.redis;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dangqian.zll
 * @date 2023/6/16
 */
public class JedisTest01 {

    @Test
    public void test01() {
        JedisPool pool = new JedisPool("localhost", 6379);

        try (Jedis jedis = pool.getResource()) {
            // Store & Retrieve a simple string
            {
                System.out.println("jedis:1: foo=" + jedis.get("foo"));
                jedis.set("foo", "bar-jedis-" + System.currentTimeMillis());
                System.out.println("jedis:2: foo=" + jedis.get("foo"));
            }
            // Store & Retrieve a HashMap
            Map<String, String> hash = new HashMap<>();
            ;
            hash.put("name", "John");
            hash.put("surname", "Smith");
            hash.put("company", "Redis");
            hash.put("age", "29");
            jedis.hset("user-session:123", hash);
            System.out.println(jedis.hgetAll("user-session:123"));
            // Prints: {name=John, surname=Smith, company=Redis, age=29}
        }
    }
}
