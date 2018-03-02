package com.github.btpka3.first.spring.boot.data.geode

import org.apache.geode.cache.Region
import org.apache.geode.cache.client.ClientCache
import org.apache.geode.cache.client.ClientCacheFactory
import org.apache.geode.cache.client.ClientRegionShortcut

class Test1 {
    static void main(String[] args) {

        ClientCache cache = new ClientCacheFactory()
                .addPoolLocator("localhost", 10334)
                .create();

        Region<String, String> region = cache
                .<String, String> createClientRegionFactory(ClientRegionShortcut.CACHING_PROXY)
                .create("regionA");

        region.put("1", "Hello");
        region.put("2", "World");

        for (Map.Entry<String, String> entry : region.entrySet()) {
            System.out.format("key = %s, value = %s\n", entry.getKey(), entry.getValue());
        }
        cache.close();
    }
}
