package com.github.btpka3.first.rocksdb;

import org.junit.Test;
import org.rocksdb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Test03 {

    Logger logger = LoggerFactory.getLogger(Test03.class);


    /**
     * 测试 column family
     */
    @Test
    public void test1() {
        RocksDB.loadLibrary();


        try (
                final ColumnFamilyOptions cfOpts = new ColumnFamilyOptions()
                        .optimizeUniversalStyleCompaction();
        ) {


            final DBOptions dbOptions = new DBOptions();
            dbOptions.setCreateIfMissing(true)
                    .setCreateMissingColumnFamilies(true);

            final List<ColumnFamilyDescriptor> cfDescriptors = Arrays.asList(
                    new ColumnFamilyDescriptor(RocksDB.DEFAULT_COLUMN_FAMILY, cfOpts)

                    // 必须全部列全，否则：RocksDBException: You have to open all column families. Column families not opened: bb, aa
                    , new ColumnFamilyDescriptor("aa".getBytes(), cfOpts)
                    , new ColumnFamilyDescriptor("bb".getBytes(), cfOpts)
            );

            final List<ColumnFamilyHandle> cfHandleList = new ArrayList<>();

            try (final RocksDB db = RocksDB.open(
                    dbOptions,
                    "/tmp/first-rocksdb/test03",
                    cfDescriptors,
                    cfHandleList
            )) {

                try {
                    cfHandleList.forEach(h -> logger.debug("h.name = " + new String(h.getName())));
                    ColumnFamilyHandle handlerDefault = cfHandleList.get(0);
                    testColumnFamily(db, handlerDefault);

//                    ColumnFamilyHandle handlerAa = cfHandleList.get(1);
//                    testColumnFamily(db, handlerAa);

                    System.out.println("112233");

                } finally {
                    // NOTE frees the column family handles before freeing the db
                    cfHandleList.forEach(AbstractImmutableNativeReference::close);
                }

            } catch (final Exception e) {
                System.out.format("Caught the expected exception -- %s\n", e);
            }
        }


        System.out.println("778899");

    }

    private void testColumnFamily(
            RocksDB db,
            ColumnFamilyHandle cfHandler
    ) throws RocksDBException {
        String cfName = new String(cfHandler.getName());
        byte[] key = (cfName + "_key_1").getBytes();
        byte[] value1 = db.get(cfHandler, key);
        logger.debug(cfName + ".get1 : " + (value1 != null ? new String(value1) : null));
        db.delete(cfHandler, key);

        // 保存新值
        String newValue = cfName + "_value_1 @ " + new Date();
        logger.debug(cfName + ".put1 : " + newValue);
        db.put(cfHandler, key, newValue.getBytes());

        // 测试内存中取值
        byte[] value2 = db.get(cfHandler, key);
        logger.debug(cfName + ".get2 : " + (value2 != null ? new String(value2) : null));

        db.put(
                (cfName + "_key_2").getBytes(),
                (cfName + "_value_2 @ " + new Date()).getBytes()
        );

        //logger.debug("rocksdb.estimate-num-keys = " + db.getProperty(cfHandler, "rocksdb.estimate-num-keys”"));

        Map<String, String> values1 = new LinkedHashMap<>();
        RocksIterator it1 = db.newIterator(cfHandler);
        it1.seekToFirst();
        while (it1.isValid()) {
            values1.put(new String(it1.key()), new String(it1.value()));

            // 清空
            db.delete(cfHandler, it1.key());

            it1.next();
        }
        // 重要
        it1.close();
        logger.debug((cfName + ".values1 = " + values1));


        if (!"default".equals(cfName)) {

            // 这里仅仅删除了 ColumnFamily, 但并不会删除数据，仍然可以查询到数据
            //db.dropColumnFamily(cfHandler);
            //db.compactRange();

            Map<String, String> values2 = new LinkedHashMap<>();
            RocksIterator it2 = db.newIterator(cfHandler);
            it2.seekToFirst();
            while (it2.isValid()) {
                values2.put(new String(it2.key()), new String(it2.value()));
                it2.next();
            }
            logger.debug((cfName + ".values2 = " + values2));

            // 重要
            it2.close();
        }
    }
}
