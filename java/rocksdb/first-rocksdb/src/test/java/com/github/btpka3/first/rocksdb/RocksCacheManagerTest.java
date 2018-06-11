package com.github.btpka3.first.rocksdb;

import com.esotericsoftware.kryo.Kryo;
import com.github.btpka3.first.rocksdb.service.*;
import org.junit.Test;
import org.rocksdb.RocksDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

public class RocksCacheManagerTest {


    Logger logger = LoggerFactory.getLogger(Test01.class);


    @Test
    public void test01() {

        RocksDB db = null;

        Kryo kryo = new Kryo();

        RocksCacheManagerConfiguration cacheMgrConfig = new RocksCacheManagerConfiguration();
        cacheMgrConfig.setDbPath("/tmp/first-rocksdb/rocksCacheMgr01");
        // defaultCacheConfig.setDbOptions(null);
        cacheMgrConfig.setKeySerializer(new RocksKryoSerializer<>(kryo, String.class));
        cacheMgrConfig.setValueSerializer(new RocksKryoSerializer<>(kryo, Map.class));

        final String CHACHE_DEFAULT = "default";
        final String CACHE_A = "aa";
        final String CACHE_B = "bb";

        Map<String, RocksCacheConfiguration> cacheConfs = new LinkedHashMap<>(4);

        final RocksCacheConfiguration CACHE_A_CONF = new RocksCacheConfiguration();
        CACHE_A_CONF.setValueSerializer(new RocksKryoSerializer<>(kryo, PojoA.class));
        cacheConfs.put(CACHE_A, CACHE_A_CONF);

        cacheConfs.put(CHACHE_DEFAULT, null);


        final RocksCacheConfiguration CACHE_B_CONF = new RocksCacheConfiguration();
        CACHE_B_CONF.setValueSerializer(new RocksKryoSerializer<>(kryo, PojoB.class));
        cacheConfs.put(CACHE_B, CACHE_B_CONF);

        cacheMgrConfig.setCacheConfs(cacheConfs);

        RocksCacheManager cacheManager = new RocksCacheManager(
                cacheMgrConfig
        );

        // 模拟 spring 初始化
        cacheManager.afterPropertiesSet();

        RocksCache cacheA = (RocksCache) cacheManager.getCache(CACHE_A);

        String key = "key";
        PojoA valueA1 = new PojoA();
        valueA1.setName("zhang3");
        PojoB valueB1 = new PojoB();
        valueB1.setAge(33);

        cacheA.put(key, valueA1);

        RocksCache cacheB = (RocksCache) cacheManager.getCache(CACHE_B);
        cacheB.put(key, valueB1);

        logger.debug("1. cacheA.get(key) = " + cacheA.get(key).get());
        logger.debug("1. cacheB.get(key) = " + cacheB.get(key).get());


    }
}
