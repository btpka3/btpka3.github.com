package com.github.btpka3.first.rocksdb.service;

import org.rocksdb.ColumnFamilyHandle;
import org.rocksdb.RocksDB;


/**
 * 运行时创建的类。
 */
public class RocksCacheContext {

    private RocksDB db;
    private String cacheName;
    private ColumnFamilyHandle columnFamilyHandle;

    private RocksSerializer<?> keySerializer;
    private RocksSerializer<?> valueSerializer;

    public RocksDB getDb() {
        return db;
    }

    public void setDb(RocksDB db) {
        this.db = db;
    }



    public ColumnFamilyHandle getColumnFamilyHandle() {
        return columnFamilyHandle;
    }

    public void setColumnFamilyHandle(ColumnFamilyHandle columnFamilyHandle) {
        this.columnFamilyHandle = columnFamilyHandle;
    }

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

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }
}
