package com.github.btpka3.first.rocksdb;

public class PojoA {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PojoA{" +
                "name='" + name + '\'' +
                '}';
    }
}
