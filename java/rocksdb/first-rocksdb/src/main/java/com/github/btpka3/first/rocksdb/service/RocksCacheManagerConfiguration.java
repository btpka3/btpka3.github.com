package com.github.btpka3.first.rocksdb.service;

import org.rocksdb.DBOptions;

import java.util.Map;

public class RocksCacheManagerConfiguration {

    private static final DBOptions DEFAULT_DB_OPTIONS = new DBOptions()
            .setCreateIfMissing(true)
            .setCreateMissingColumnFamilies(true);

    //    private boolean enableCreateCache;
    private String dbPath;

    private DBOptions dbOptions = DEFAULT_DB_OPTIONS;
    /**
     * key: cache name, 同时也是 rocksdb 的 column family 名称。
     * value : 特定 cache family 的 配置。
     */
    private Map<String, RocksCacheConfiguration> cacheConfs;

    private RocksSerializer<?> keySerializer;
    private RocksSerializer<?> valueSerializer;


    public RocksSerializer<?> getKeySerializer() {
        return keySerializer;
    }

    public void setKeySerializer(RocksSerializer<?> keySerializer) {
        this.keySerializer = keySerializer;
    }

    public RocksSerializer<?> getValueSerializer() {
        return valueSerializer;
    }

    public void setValueSerializer(RocksSerializer<?> valueSerializer) {
        this.valueSerializer = valueSerializer;
    }


    public String getDbPath() {
        return dbPath;
    }

    public void setDbPath(String dbPath) {
        this.dbPath = dbPath;
    }

    public DBOptions getDbOptions() {
        return dbOptions;
    }

    public void setDbOptions(DBOptions dbOptions) {
        this.dbOptions = dbOptions;
    }


    public Map<String, RocksCacheConfiguration> getCacheConfs() {
        return cacheConfs;
    }

    public void setCacheConfs(Map<String, RocksCacheConfiguration> cacheConfs) {
        this.cacheConfs = cacheConfs;
    }
}
