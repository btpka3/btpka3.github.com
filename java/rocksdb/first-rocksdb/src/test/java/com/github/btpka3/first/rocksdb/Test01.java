package com.github.btpka3.first.rocksdb;

import org.junit.Test;
import org.rocksdb.*;
import org.rocksdb.util.SizeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class Test01 {

    Logger logger = LoggerFactory.getLogger(Test01.class);

    @Test
    public void test1() {
        RocksDB.loadLibrary();


        try (final Options options = new Options();
             final Filter bloomFilter = new BloomFilter(10);
             final ReadOptions readOptions = new ReadOptions().setFillCache(false);
             final Statistics stats = new Statistics();
             final RateLimiter rateLimiter = new RateLimiter(10000000, 10000, 10);
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


            // 注意 ： ttl 支持
            try (final RocksDB db = RocksDB.open(options, "/tmp/first-rocksdb/test01")) {

                byte[] key = "hi".getBytes();
                byte[] value1 = db.get(readOptions, key);

                if (value1 == null) {
                    logger.debug("get1 : null");
                } else {
                    logger.debug("get1 : " + new String(value1));
                    db.delete(key);
                }

                String newValue = "world @ " + new Date();
                logger.debug("put1 : " + newValue);
                db.put(key, newValue.getBytes());

                byte[] value2 = db.get(readOptions, key);

                if (value2 == null) {
                    logger.debug("get2 : null");
                } else {
                    logger.debug("get2 : " + new String(value2));
                    db.delete(key);
                }

            } catch (final RocksDBException e) {
                logger.debug(e.getMessage(), e);
            }
        }


    }
}
