package com.github.btpka3.first.rockdb;

import com.aliyun.tair.taircpc.TairCpc;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * tair-sdk 主要 for 业务：
 *
 * @author dangqian.zll
 * @date 2023/3/1
 * @see <a href="https://help.aliyun.com/document_detail/443880.html">Tair-SDK</a>
 * @see <a href="https://github.com/alibaba/alibabacloud-tairjedis-sdk">github : alibaba/alibabacloud-tairjedis-sdk</a>
 * @see <a href="https://help.aliyun.com/document_detail/443818.html">TairCpc</a>
 * @see <a href="https://github.com/alibaba/alibabacloud-tairjedis-sdk/tree/master/src/test/java/com/aliyun/tair/tests/example">example</a>
 */
public class TairSdkTest {

    // init timeout
    private static final int DEFAULT_CONNECTION_TIMEOUT = 5000;
    // api timeout
    private static final int DEFAULT_SO_TIMEOUT = 2000;
    private static final String HOST = "r-xxx.redis.rds.aliyuncs.com";
    private static final int PORT = 6379;
    private static final String PASSWORD = null;


    @Test
    public void test01() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(32);
        config.setMaxIdle(32);
        config.setMaxIdle(20);
        JedisPool jedisPool = new JedisPool(config, HOST, PORT, DEFAULT_CONNECTION_TIMEOUT,
                DEFAULT_SO_TIMEOUT, PASSWORD, 0, null);
        TairCpc tairCpc = new TairCpc(jedisPool);
        String key = "key";
        String field = "item";

        cpcUpdate(tairCpc, key, field);

        cpcEstimate(tairCpc, key);

    }

    /**
     * 在指定TairCpc中添加item。若TairCpc不存在则自动新建，若待添加的item已存在于目标TairCpc中，则不会进行操作。
     *
     * @param tairCpc
     * @param key
     * @param field
     * @return
     */
    public boolean cpcUpdate(TairCpc tairCpc, String key, String field) {
        try {
            String result = tairCpc.cpcUpdate(key, field);
            if ("OK".equals(result)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * https://help.aliyun.com/document_detail/443818.html
     *
     * @param tairCpc
     * @param key
     * @return
     */
    public double cpcEstimate(TairCpc tairCpc, String key) {
        try {
            return tairCpc.cpcEstimate(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }


}
