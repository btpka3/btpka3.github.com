package io.github.btpka3.first.flink;

import org.apache.flink.table.catalog.Catalog;
import org.apache.flink.table.catalog.GenericInMemoryCatalog;

/**
 * @author dangqian.zll
 * @date 2024/7/5
 */
public class CatalogTest {

    public void x() {
        Catalog c = new GenericInMemoryCatalog("a");
    }
}
