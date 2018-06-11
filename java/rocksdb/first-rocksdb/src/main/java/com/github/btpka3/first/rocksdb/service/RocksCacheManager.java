package com.github.btpka3.first.rocksdb.service;

import org.rocksdb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.util.Assert;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 使用 RocksDB 作为缓存
 */
public class RocksCacheManager extends AbstractCacheManager implements DisposableBean {
    Logger logger = LoggerFactory.getLogger(RocksCacheManager.class);

    private RocksCacheManagerConfiguration cacheManagerConfiguration;

    private RocksDB db;

    public RocksCacheManager(
            RocksCacheManagerConfiguration cacheManagerConfiguration
    ) {
        this.cacheManagerConfiguration = cacheManagerConfiguration;
    }


    @Override
    protected Collection<RocksCache> loadCaches() {

        Map<String, RocksCacheConfiguration> cacheConfs = cacheManagerConfiguration.getCacheConfs();
        Assert.isTrue(cacheConfs != null && cacheConfs.size() > 0, "There is not rocks cache configured.");

        final List<ColumnFamilyDescriptor> cfDescriptors = cacheConfs.entrySet().stream()
                .map(entry -> {
                    String cacheName = entry.getKey();
                    RocksCacheConfiguration cacheConf = entry.getValue();
                    byte[] cacheNameBytes;
                    try {
                        cacheNameBytes = cacheName.getBytes("UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }

                    ColumnFamilyOptions cfOptions = null;
                    if (cacheConf != null) {
                        cfOptions = cacheConf.getColumnFamilyOptions();
                    }
                    return cfOptions != null
                            ? new ColumnFamilyDescriptor(cacheNameBytes, cfOptions)
                            : new ColumnFamilyDescriptor(cacheNameBytes);
                })
                .collect(Collectors.toList());

        final List<ColumnFamilyHandle> cfHandleList = new ArrayList<>();


        try {
            db = RocksDB.open(
                    cacheManagerConfiguration.getDbOptions(),
                    cacheManagerConfiguration.getDbPath(),
                    cfDescriptors,
                    cfHandleList
            );
        } catch (RocksDBException e) {
            throw new RuntimeException(e);
        }
        List<RocksCache> caches = null;
        try {
            caches = cacheConfs.entrySet().stream()
                    .map(entry -> {

                        String cacheName = entry.getKey();
                        RocksCacheConfiguration cacheConf = entry.getValue();


                        RocksCacheContext cacheContext = new RocksCacheContext();
                        cacheContext.setDb(db);
                        cacheContext.setCacheName(cacheName);

                        ColumnFamilyHandle cfHandle = cfHandleList.stream()
                                .filter(handle -> {
                                    try {
                                        return Objects.equals(entry.getKey(), new String(handle.getName(), "UTF-8"));
                                    } catch (UnsupportedEncodingException e) {
                                        throw new RuntimeException(e);
                                    }
                                })
                                .findAny()
                                .orElse(null);
                        Assert.isTrue(cfHandle != null, "config error : cfHandle not found");
                        cacheContext.setColumnFamilyHandle(cfHandle);

                        RocksSerializer<?> keySerializer = null;


                        if (cacheConf != null && cacheConf.getKeySerializer() != null) {
                            keySerializer = cacheConf.getKeySerializer();
                        }
                        if (keySerializer == null) {
                            keySerializer = cacheManagerConfiguration.getKeySerializer();
                        }

                        Assert.isTrue(keySerializer != null, () -> "config error : key serializer not specified for `" + cacheName + "`.");
                        cacheContext.setKeySerializer(keySerializer);


                        RocksSerializer<?> valueSerializer = null;
                        if (cacheConf != null && cacheConf.getValueSerializer() != null) {
                            valueSerializer = cacheConf.getValueSerializer();
                        }
                        if (valueSerializer == null) {
                            valueSerializer = cacheManagerConfiguration.getValueSerializer();
                        }

                        Assert.isTrue(valueSerializer != null, () -> "config error : value serializer not specified for `" + cacheName + "`.");
                        cacheContext.setValueSerializer(valueSerializer);

                        return createRocksCache(cacheContext);
                    })
                    .collect(Collectors.toList());


            return caches;
        } catch (Exception e) {
//            if (caches != null) {
//                caches.forEach(cache -> {
//                    RocksCacheContext rocksCacheContext = cache.getCacheContext();
//                    if (rocksCacheContext == null) {
//                        return;
//                    }
//                    ColumnFamilyHandle cfHandle = rocksCacheContext.getColumnFamilyHandle();
//                    if (cfHandle != null) {
//                        cfHandle.close();
//                    }
//                });
//            }
            throw new RuntimeException(e);
        }
    }


    @Override
    protected Cache getMissingCache(String name) {


        // 防范措施，rocksdb 的 column family 在打开时必须全部指定

        throw new RuntimeException("create rocksdb cache at runtime is not supported");

//        logger.warn("create rockdb cache '" + name
//                + "', please make sure new column family is add to config before restart"
//        );

    }

    protected RocksCache createRocksCache(
            RocksCacheContext cacheContext
    ) {
        return new RocksCache(cacheContext);
    }

    @Override
    public void destroy() throws Exception {


        this.getCacheNames().forEach(cacheName -> {
            RocksCache cache = (RocksCache) this.getCache(cacheName);
            RocksCacheContext cacheContext = cache.getCacheContext();
            cacheContext.getColumnFamilyHandle().close();
        });

        db.close();
    }
}
