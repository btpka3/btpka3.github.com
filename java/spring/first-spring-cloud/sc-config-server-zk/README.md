
# 运行

```bash
docker-compose -f sc-config-server-zk/src/test/docker/docker-compose.yml up

# 先熟悉一下 zookeeper 命令行操作
docker exec -it docker_zookeeper_1 zkCli.sh help
    help                        # 任何不支持的命令都显示完成的命令列表
    ls /                        # 查看根节点
    create /a "aaa"             # 创建节点 /a 并设置数据
    get /a
    create /a/b "bbb"           # 创建节点 /b并设置数据
    get /a
    get /b
    set /a "a00"                # 更新数据
    rmr /a                      # 删除
    quit                        # 退出

# 为 sc-config-server-zk 创建配置
alias zkCli="docker exec -it docker_zookeeper_1 zkCli.sh"
zkCli ls /
zkCli rmr /config
zkCli create /config ''

# 注意：不是以文件内容的方式存储的，而已以 key-value 的方式进行存储的
#zkCli create /config/sc-config-server-zk \
#    "`cat sc-config-server-zk/src/test/docker/sc-config-server-zk/application.yml`"
    
#zkCli create /config/sc-config-server-zk,dev \
#     "`cat sc-config-server-zk/src/test/docker/sc-config-server-zk/application-dev.yml`"


zkCli create /config/sc-config-server-zk,dev            ''
zkCli create /config/sc-config-server-zk,dev/a          'AAA'
zkCli create /config/sc-config-server-zk,dev/b          'bbb'

zkCli create /config/sc-config-server-zk                ''
zkCli create /config/sc-config-server-zk/server.port    '10060'
zkCli create /config/sc-config-server-zk/a              'aaa'
zkCli create /config/sc-config-server-zk/x              ''
zkCli create /config/sc-config-server-zk/x/y            ''
zkCli create /config/sc-config-server-zk/x/y/z          'xyz'

# 方便本地通过 spring-boot-actuator 访问 /refresh 
zkCli create /config/sc-config-server-zk/management.security            ''
zkCli create /config/sc-config-server-zk/management.security/enabled    'false'


# 启动
./gradlew :sc-config-server-zk:bootRun

# 验证
curl -v http://localhost:10060/hi
{"server.port":"10060","a":"AAA","b":"bbb","x.y.z":"xyz","date":"Sat Dec 02 15:46:55 CST 2017"}

# 更新
zkCli set    /config/sc-config-server-zk/x/y/z          'xyz_999~'
# 立即查看，就已经更新了
curl -v http://localhost:10060/hi

# 手动命令更新
curl -v -XPOST http://localhost:10060/refresh
curl -v http://localhost:10060/hi
```



# 参考
- org.springframework.cloud.zookeeper.ZookeeperAutoConfiguration
- org.springframework.cloud.zookeeper.ZookeeperProperties
- org.springframework.cloud.zookeeper.config.ZookeeperConfigAutoConfiguration
- org.springframework.cloud.zookeeper.config.ZookeeperConfigProperties
- org.springframework.cloud.zookeeper.config.ZookeeperConfigBootstrapConfiguration
- org.springframework.cloud.zookeeper.config.ZookeeperPropertySource
    该类应当算是一个很有用的类，哪怕用在业务开发中，比如系统配置表中数据。
    不用每次都去查询数据库，或者Cache+轮训。

- org.springframework.cloud.endpoint.RefreshEndpoint
- [ZooKeeper Commands: The Four Letter Words](http://zookeeper.apache.org/doc/r3.3.1/zookeeperAdmin.html#sc_zkCommands)

# 总结

- Spring Cloud Zookeeper Config 是 Spring Cloud Config 的另外一种替代方式。不能同时使用。
- 需要先启动 zookeeper，并写入相关配置
- 层级配置 比如 "x.y.z: xyz" 可以在 zookeeper 中方式配置：

    - '/x.y.z' = 'xyz'
    - '/x/y/z' = 'xyz'
    - '/x/y.z' = 'xyz'
    - '/x.y/z' = 'xyz'

# FIXME
- 对标 yaml 配置文件中 的 List,Map,Zookeeper该如何配置？

