package com.github.btpka3.first.rockdb;

import org.junit.jupiter.api.Test;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

import java.nio.charset.StandardCharsets;

/**
 * @author dangqian.zll
 * @date 2023/3/1
 * @see <a href="https://github.com/facebook/rocksdb/wiki/RocksJava-Basics">RocksJava-Basics</a>
 */
public class RocksDBTest {


    @Test
    public void test01() {
        String dbPath = "/tmp/first-rocksdb.db";
        // a static method that loads the RocksDB C++ library.
        RocksDB.loadLibrary();
        try (final Options options = new Options().setCreateIfMissing(true)) {

            // a factory method that returns a RocksDB instance
            try (final RocksDB db = RocksDB.open(options, dbPath)) {

                String keyStr = "a";
                byte[] keyBytes = keyStr.getBytes(StandardCharsets.UTF_8);
                System.out.println("get : key = `" + keyStr + "` , value = " + getValue(db, keyBytes));

                String value = "aaa-" + System.currentTimeMillis();
                db.put(keyBytes, value.getBytes(StandardCharsets.UTF_8));

                System.out.println("put : key = `" + keyStr + "` , value = " + value);
                // do something
            }
        } catch (Throwable e) {
            System.err.println("err : " + e.getMessage());
            e.printStackTrace();
            // do some error handling
        }
    }

    protected String getValue(RocksDB db, byte[] key) throws RocksDBException {
        final byte[] value = db.get(key);
        if (value == null) {
            return null;
        }
        return new String(value, StandardCharsets.UTF_8);
    }
}
