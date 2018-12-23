package com.github.btpka3.spring.cache.db;

import org.springframework.cache.support.AbstractValueAdaptingCache;

import java.util.concurrent.Callable;

public class DbCache extends AbstractValueAdaptingCache {

    /**
     * Create an {@code AbstractValueAdaptingCache} with the given setting.
     *
     * @param allowNullValues whether to allow for {@code null} values
     */
    protected DbCache(boolean allowNullValues) {
        super(allowNullValues);
    }

    @Override
    protected Object lookup(Object key) {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Object getNativeCache() {
        return null;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        return null;
    }

    @Override
    public void put(Object key, Object value) {

    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        return null;
    }

    @Override
    public void evict(Object key) {

    }

    @Override
    public void clear() {

    }
}
