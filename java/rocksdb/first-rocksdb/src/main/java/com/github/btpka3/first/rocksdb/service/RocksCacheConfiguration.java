package com.github.btpka3.first.rocksdb.service;

import org.rocksdb.ColumnFamilyOptions;

public class RocksCacheConfiguration {

    private ColumnFamilyOptions columnFamilyOptions;

    private RocksSerializer<?> keySerializer;
    private RocksSerializer<?> valueSerializer;



    public RocksSerializer<?> getKeySerializer() {
        return keySerializer;
    }

    public void setKeySerializer(RocksSerializer<?> keySerializer) {
        this.keySerializer = keySerializer;
    }

    public RocksSerializer<?> getValueSerializer() {
        return valueSerializer;
    }

    public void setValueSerializer(RocksSerializer<?> valueSerializer) {
        this.valueSerializer = valueSerializer;
    }


    public ColumnFamilyOptions getColumnFamilyOptions() {
        return columnFamilyOptions;
    }

    public void setColumnFamilyOptions(ColumnFamilyOptions columnFamilyOptions) {
        this.columnFamilyOptions = columnFamilyOptions;
    }
}
