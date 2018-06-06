package com.github.btpka3.first.rocksdb;

import org.junit.Test;
import org.rocksdb.*;
import org.rocksdb.util.SizeUnit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Test02 {

    @Test
    public void test1() {
        RocksDB.loadLibrary();

        String table = "myTest";

        try (final Options options = new Options();
             final Filter bloomFilter = new BloomFilter(10);
             final ReadOptions readOptions = new ReadOptions().setFillCache(false);
             final Statistics stats = new Statistics();
             final RateLimiter rateLimiter = new RateLimiter(10000000, 10000, 10);
             final ColumnFamilyOptions cfOpts = new ColumnFamilyOptions().optimizeUniversalStyleCompaction();
             final DBOptions dbOptions = new DBOptions();
        ) {

            final BlockBasedTableConfig tableConf = new BlockBasedTableConfig();
            tableConf.setBlockCacheSize(64 * SizeUnit.KB)
                    .setFilter(bloomFilter)
                    .setCacheNumShardBits(6)
                    .setBlockSizeDeviation(5)
                    .setBlockRestartInterval(3)
                    .setCacheIndexAndFilterBlocks(true)
                    .setHashIndexAllowCollision(false)
                    .setBlockCacheCompressedSize(64 * SizeUnit.KB)
                    .setBlockCacheCompressedNumShardBits(10);

            options.setCreateIfMissing(true)
                    .setTableFormatConfig(tableConf)
                    .setStatistics(stats)
                    .setWriteBufferSize(8 * SizeUnit.KB)
                    .setMaxWriteBufferNumber(3)
                    .setMaxBackgroundCompactions(10)
                    .setWalTtlSeconds(10)
                    .setRateLimiter(rateLimiter)
                    .setCompressionType(CompressionType.SNAPPY_COMPRESSION)
                    .setCompactionStyle(CompactionStyle.UNIVERSAL);

            dbOptions.setCreateIfMissing(true)
                    .setCreateMissingColumnFamilies(true);

            final List<ColumnFamilyDescriptor> cfDescriptors = Arrays.asList(
                    new ColumnFamilyDescriptor(RocksDB.DEFAULT_COLUMN_FAMILY, cfOpts),
                    new ColumnFamilyDescriptor("myTest".getBytes(), cfOpts),
                    new ColumnFamilyDescriptor("my-first-columnfamily".getBytes(), cfOpts)
            );

            final List<ColumnFamilyHandle> columnFamilyHandleList = new ArrayList<>();
            List<Integer> ttlValues = Arrays.asList(5 * 1000,
                    5 * 1000,
                    5 * 1000
            );

            // 注意 ： ttl 支持
            try (final TtlDB db = TtlDB.open(dbOptions,
                    "/tmp/first-rocksdb/test02",
                    cfDescriptors,
                    columnFamilyHandleList,
                    ttlValues,
                    false)) {


                ColumnFamilyHandle columnFamilyHandle = db.createColumnFamily(new ColumnFamilyDescriptor(table.getBytes(), new ColumnFamilyOptions()));


                byte[] key = "hi".getBytes();
                byte[] value1 = db.get(columnFamilyHandle, readOptions, key);

                if (value1 != null) {
                    System.out.format("Get('hello') = `%s`, then delete it\n", new String(value1));
                    db.delete(key);
                }

                String newValue = "world @ " + new Date();
                System.out.println("value is null, put new value : " + newValue);
                db.put(columnFamilyHandle, key, newValue.getBytes());

                // 验证 TTL 前能读取到
                try {
                    Thread.sleep(3 * 1000);
                    byte[] value2 = db.get(readOptions, key);
                    if (value2 == null) {
                        System.out.println(" read2 : null");
                    } else {
                        System.out.println(" read2 : " + new String(value2));
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }


                // 验证 TTL 后能读取不到 FIXME not work
                try {
                    Thread.sleep(3 * 1000);
                    byte[] value3 = db.get(readOptions, key);
                    if (value3 == null) {
                        System.out.println(" read3 : null");
                    } else {
                        System.out.println(" read3 : " + new String(value3));
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                List<ColumnFamilyHandle> handleList = new ArrayList<>();
                handleList.add(columnFamilyHandle);

                db.multiGet(handleList, Arrays.asList(key)).forEach((keyBytes, valueBytes) -> {
                    System.out.println("---- " + new String(keyBytes) + " : " + new String(valueBytes));
                });


            } catch (final RocksDBException e) {
                System.out.format("Caught the expected exception -- %s\n", e);
            }
        }


    }
}
