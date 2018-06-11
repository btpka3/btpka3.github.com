package com.github.btpka3.first.rocksdb.service;

public interface RocksSerializer<T> {

    T deserialize(byte[] bytes);

    byte[] serialize(T t);

}
