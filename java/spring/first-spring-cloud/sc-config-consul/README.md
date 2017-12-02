

# 参考

- org.springframework.cloud.consul.ConsulAutoConfiguration
- org.springframework.cloud.consul.ConsulProperties
- org.springframework.cloud.consul.config.ConsulConfigAutoConfiguration
- org.springframework.cloud.consul.config.ConsulConfigBootstrapConfiguration


# 运行

```bash
# 启动 consul 服务
docker-compose -f sc-config-consul/src/test/docker/docker-compose.yml up

# 通过 consul 对 sc-config-consul 进行配置
alias consul="docker exec -it docker_consul_1 consul"
consul 
consul kv put config/sc-config-consul,dev/a         'AAA' 
consul kv put config/sc-config-consul,dev/b         'bbb'
 
consul kv put config/sc-config-consul/server.port   '10070'
consul kv put config/sc-config-consul/a             'aaa'
consul kv put config/sc-config-consul/x/y/z         'xyz'

consul kv put config/sc-config-consul/management.security/enabled   'false'

# 启动
./gradlew :sc-config-consul:bootRun

# 验证
curl -v http://localhost:10070/hi
{"server.port":"10070","a":"AAA","b":"bbb","x.y.z":"xyz","date":"Sat Dec 02 21:15:35 CST 2017"}

# 更新
consul kv put config/sc-config-consul/x/y/z         'xyz_9997'
# 过3秒后，进行确认，已经自动更新
curl -v http://localhost:10070/hi

# 手动命令更新
curl -v -XPOST http://localhost:10070/refresh
curl -v http://localhost:10070/hi
```