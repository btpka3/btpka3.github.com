package com.github.btpka3.first.rocksdb;

import org.junit.Test;
import org.rocksdb.*;
import org.rocksdb.util.SizeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Test02 {

    Logger logger = LoggerFactory.getLogger(Test02.class);

    /**
     * 测试 TTL, 插入，超时后 读取，关闭
     */
    @Test
    public void test2_01() {
        RocksDB.loadLibrary();

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
                    .setWalTtlSeconds(1)
                    .setRateLimiter(rateLimiter)
                    .setCompressionType(CompressionType.SNAPPY_COMPRESSION)
                    .setCompactionStyle(CompactionStyle.UNIVERSAL);

            // 注意 ： ttl 支持
            try (final RocksDB db = TtlDB.open(options,
                    "/tmp/first-rocksdb/test2_01",
                    // 5秒
                    5,
                    false)) {

                byte[] key = "hi_A".getBytes();
                byte[] value1 = db.get(key);

                logger.debug("get1 : " + (value1 != null ? new String(value1) : null));

                byte[] newValueA1 = ("world@hi_A " + new Date()).getBytes();
                db.put(key, newValueA1);

                byte[] value2 = db.get(key);
                logger.debug("get2 : " + (value2 != null ? new String(value2) : null));

                try {
                    TimeUnit.SECONDS.sleep(6);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                db.compactRange();
                byte[] value3 = db.get(key);
                logger.debug("get3 : " + (value3 != null ? new String(value3) : null));


            } catch (final RocksDBException e) {
                System.out.format("Caught the expected exception -- %s\n", e);
            }
        }
    }

    /**
     * 测试 TTL, 插入，关闭，超时后 再读取（第二次执行），
     */
    @Test
    public void test2_02() {
        RocksDB.loadLibrary();

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
                    .setWalTtlSeconds(3)
                    .setRateLimiter(rateLimiter)
                    .setCompressionType(CompressionType.SNAPPY_COMPRESSION)
                    .setCompactionStyle(CompactionStyle.UNIVERSAL);

            // 注意 ： ttl 支持
            try (final RocksDB db = TtlDB.open(options,
                    "/tmp/first-rocksdb/test2_02",
                    // 5秒
                    5,
                    false)) {

                // FIXME : 需要手动调用？
                db.compactRange();

                byte[] key = "hi_A".getBytes();
                byte[] value1 = db.get(key);

                logger.debug("get1 : " + (value1 != null ? new String(value1) : null));

                byte[] newValueA1 = ("world@hi_A " + new Date()).getBytes();
                db.put(key, newValueA1);

                byte[] value2 = db.get(key);
                logger.debug("get2 : " + (value2 != null ? new String(value2) : null));

            } catch (final RocksDBException e) {
                System.out.format("Caught the expected exception -- %s\n", e);
            }
        }
    }
}
