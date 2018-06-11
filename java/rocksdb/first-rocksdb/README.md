

## 目的
- 学习 rocksdb
    - java api
    - column family 
    - TTL
- 使用 spring cache 接口对 rocksdb 进行简单抽象


## 说明
- 一个 cache 存储的 value 的类型应该是一样的。

## 参考
- [RocksJava-Basics](https://github.com/facebook/rocksdb/wiki/RocksJava-Basics)
- [Time to Live](https://github.com/facebook/rocksdb/wiki/Time-to-Live)
- [spring-data-redis](https://projects.spring.io/spring-data-redis/)

## TODO && FIXME
- 实现 spring cache 抽象接口？
- TTL， 需要手动调用 `compactRange` 后才会清除过期的。
- `compactRange` 性能？
- 西能

