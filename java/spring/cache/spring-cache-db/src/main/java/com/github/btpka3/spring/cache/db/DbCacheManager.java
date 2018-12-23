package com.github.btpka3.spring.cache.db;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;

import java.util.Collection;

public class DbCacheManager extends AbstractCacheManager implements DisposableBean  {

    @Override
    public void destroy() throws Exception {

    }

    @Override
    protected Collection<? extends Cache> loadCaches() {
        return null;
    }
}
