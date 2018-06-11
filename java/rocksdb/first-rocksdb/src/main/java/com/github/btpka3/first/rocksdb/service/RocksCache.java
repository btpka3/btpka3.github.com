package com.github.btpka3.first.rocksdb.service;

import org.rocksdb.ColumnFamilyHandle;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.rocksdb.RocksIterator;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.cache.support.NullValue;
import org.springframework.lang.Nullable;

import java.util.concurrent.Callable;


/**
 * 使用 RocksDB 作为缓存
 */
public class RocksCache extends AbstractValueAdaptingCache {

    private final RocksCacheContext cacheContext;

    protected RocksCache(RocksCacheContext cacheContext) {
        super(false);
        this.cacheContext = cacheContext;
    }

    @Override
    protected Object lookup(Object key) {

        byte[] keyBytes = serializeCacheKey(key);

        byte[] valueBytes;
        try {
            valueBytes = cacheContext.getDb().get(cacheContext.getColumnFamilyHandle(), keyBytes);
        } catch (RocksDBException e) {
            throw new RuntimeException(e);
        }
        return deserializeCacheValue(valueBytes);
    }

    @Override
    public String getName() {
        return cacheContext.getCacheName();
    }

    @Override
    public RocksDB getNativeCache() {
        return cacheContext.getDb();
    }

    @Nullable
    protected Object preProcessCacheValue(@Nullable Object value) {

        if (value != null) {
            return value;
        }

        return isAllowNullValues() ? NullValue.INSTANCE : null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {


        ValueWrapper result = get(key);

        if (result != null) {
            return (T) result.get();
        }

        T value;
        try {
            value = valueLoader.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        put(key, value);

        return value;
//
//        byte[] keyBytes = null;
//        if (key instanceof byte[]) {
//            keyBytes = (byte[]) key;
//        } else {
//            keyBytes = configuration.getKeySerializer().serialize(key);
//        }
//
//        try {
//            byte[] value = db.get(keyBytes);
//            if (value != null) {
//                return configuration.getValueSerializer().deserialize(value, String.class);
//            }
//
//            T userValueObj = valueLoader.call();
//            byte[] userValueBytes = rocksSerializer.serialize(userValueObj);
//
//            put(keyBytes, userValueBytes);
//            return userValueObj;
//
//        } catch (Exception e) {
//            throw new RuntimeException("can not get value", e);
//        }

    }

    //    protected byte[] deserializeCacheValue(byte value) {
//        return configuration.getValueSerializer().serialize(value);
//    }
    @SuppressWarnings("unchecked")
    protected byte[] serializeCacheValue(Object value) {
        return ((RocksSerializer<Object>) cacheContext.getValueSerializer()).serialize(value);
    }

    @SuppressWarnings("unchecked")
    protected byte[] serializeCacheKey(Object value) {
        return ((RocksSerializer<Object>) cacheContext.getKeySerializer()).serialize(value);
    }

    protected Object deserializeCacheValue(byte[] value) {
        return cacheContext.getValueSerializer().deserialize(value);
    }

    @Override
    public void put(Object key, Object value) {
        Object cacheValue = preProcessCacheValue(value);

        if (!isAllowNullValues() && cacheValue == null) {
            throw new IllegalArgumentException("null value disabled : " + key);
        }

        byte[] keyBytes = serializeCacheKey(key);
        byte[] valueBytes = serializeCacheValue(value);

        try {
            cacheContext.getDb().put(cacheContext.getColumnFamilyHandle(), keyBytes, valueBytes);
        } catch (RocksDBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        Object cacheValue = preProcessCacheValue(value);

        if (!isAllowNullValues() && cacheValue == null) {
            return get(key);
        }

        put(key, value);
        return get(key);
    }

    @Override
    public void evict(Object key) {
        try {
            cacheContext.getDb().delete(serializeCacheKey(key));
        } catch (RocksDBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clear() {
        RocksDB db = cacheContext.getDb();
        ColumnFamilyHandle columnFamilyHandle = cacheContext.getColumnFamilyHandle();

        try (
                RocksIterator it = cacheContext.getDb().newIterator(columnFamilyHandle);
        ) {
            it.seekToFirst();
            while (it.isValid()) {
                byte[] key = it.key();
                db.delete(columnFamilyHandle, key);
            }
        } catch (RocksDBException e) {
            throw new RuntimeException(e);
        }
    }

    public RocksCacheContext getCacheContext() {
        return cacheContext;
    }

}
